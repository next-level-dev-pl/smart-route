"""
Bus Stops Data Importer
========================

Simple JSON bus stops data importer to PostgresSQL database.

The importer deletes all data from the table and then imports from the .json file.


organization: next-level-dev-pl
project: smart-route
url: https://github.com/next-level-dev-pl/smart-route/
autor: Dawid Bielecki ('dawciobiel')
data: 2025-02-28
version: 1.1.0


Usage:
1. Set up variables data in .env config file.

2. Run the importer:
   python3 json-data-importer.py

"""

import json
import psycopg2
import os
from dotenv import load_dotenv

# Load environment variables from .env file (if exists)
load_dotenv()

# Database configuration constants
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")
DB_HOST = os.getenv("DB_HOST", "localhost")  # Default: localhost
DB_PORT = os.getenv("DB_PORT", "5432")  # Default PostgreSQL port

# Read JSON
with open(os.getenv("JSON_FILE"), "r") as f:
    data = json.load(f)

# Initialize counters for summary
added_records = 0
skipped_records = 0

# Database connection
try:
    conn = psycopg2.connect(
        dbname=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD,
        host=DB_HOST,
        port=DB_PORT
    )
    cur = conn.cursor()

    # Step 1: Delete all existing data from bus_stops table
    print("Deleting all existing records from bus_stops table...")
    cur.execute("DELETE FROM bus_stops;")
    # Optionally, reset the sequence if you use auto-incrementing fields (e.g., SERIAL)
    # cur.execute("TRUNCATE TABLE bus_stops RESTART IDENTITY;")
    print("All records deleted successfully.")

    # Step 2: Insert new data
    for item in data["result"]:
        values_dict = {val["key"]: val["value"] for val in item["values"]}

        # Extract geographical coordinates
        szer = values_dict.get("szer_geo")
        dlug = values_dict.get("dlug_geo")

        # Check if coordinates are valid
        if szer and dlug:
            try:
                # Ensure valid latitude/longitude range
                szer = float(szer)
                dlug = float(dlug)
                if not (-90 <= szer <= 90) or not (-180 <= dlug <= 180):
                    raise ValueError(f"Invalid coordinates: lat={szer}, lon={dlug}")

                # Insert into database
                cur.execute("""
                    INSERT INTO bus_stops (stop_id, stop_nr, stop_id_name, street_id, location, direction, valid_from)
                    VALUES (%s, %s, %s, %s, ST_GeographyFromText(%s), %s, %s)
                """, (
                    values_dict.get("zespol"),
                    values_dict.get("slupek"),
                    values_dict.get("nazwa_zespolu"),
                    values_dict.get("id_ulicy"),
                    f'POINT({dlug} {szer})',  # WKT format: POINT(longitude latitude)
                    values_dict.get("kierunek"),
                    values_dict.get("obowiazuje_od")
                ))

                added_records += 1  # Increment counter for added records

            except ValueError as e:
                print(f"Warning: Skipping invalid coordinates for stop {values_dict.get('zespol')}: {e}")
                skipped_records += 1  # Increment counter for skipped records
                continue
        else:
            print(f"Warning: Missing coordinates for stop {values_dict.get('zespol')}, skipping.")
            skipped_records += 1  # Increment counter for skipped records

    conn.commit()

except psycopg2.DatabaseError as e:
    print(f"Database error: {e}")

finally:
    if conn:
        cur.close()
        conn.close()

# Summary report
print("\n==== Import Summary ====")
print(f"Total records processed: {len(data['result'])}")
print(f"Records added to database: {added_records}")
print(f"Records skipped (due to errors or missing data): {skipped_records}")
print("=========================")