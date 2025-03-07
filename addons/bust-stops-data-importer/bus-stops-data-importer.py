"""
Bus Stops Data Importer
========================

Simple JSON bus stops data importer to PostgresSQL database.

organization: next-level-dev-pl
project: smart-route
url: https://github.com/next-level-dev-pl/smart-route/
autor: Dawid Bielecki ('dawciobiel')
data: 2025-02-28
version: 1.0.0


Usage:
1. First create the bus_stops table:

    CREATE TABLE bus_stops (
       zespol TEXT,
       slupek TEXT,
       nazwa_zespolu TEXT,
       id_ulicy TEXT,
       szer_geo NUMERIC,
       dlug_geo NUMERIC,
       kierunek TEXT,
       obowiazuje_od TIMESTAMP
   );

2. Run the importer:
   python3 bus-stops-data-importer.py

"""

import json
import psycopg2
import os
from dotenv import load_dotenv

# Load environment variables from .env file (if exists)
load_dotenv()

# Database configuration constants
DB_NAME = os.getenv("DB_NAME", "smartroute")
DB_USER = os.getenv("DB_USER", "user")
DB_PASSWORD = os.getenv("DB_PASSWORD", "password")
DB_HOST = os.getenv("DB_HOST", "localhost")  # Default: localhost
DB_PORT = os.getenv("DB_PORT", "5432")  # Default PostgreSQL port

# Read JSON
with open("bus_stops.json", "r") as f:
    data = json.load(f)

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

    for item in data["result"]:
        values_dict = {val["key"]: val["value"] for val in item["values"]}

        cur.execute("""
            INSERT INTO bus_stops (zespol, slupek, nazwa_zespolu, id_ulicy, szer_geo, dlug_geo, kierunek, obowiazuje_od)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
        """, (
            values_dict.get("zespol"),
            values_dict.get("slupek"),
            values_dict.get("nazwa_zespolu"),
            values_dict.get("id_ulicy"),
            values_dict.get("szer_geo"),
            values_dict.get("dlug_geo"),
            values_dict.get("kierunek"),
            values_dict.get("obowiazuje_od")
        ))

    conn.commit()

except psycopg2.DatabaseError as e:
    print(f"Database error: {e}")

finally:
    if conn:
        cur.close()
        conn.close()

