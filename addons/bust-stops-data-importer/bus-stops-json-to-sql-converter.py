"""
Bus Stops Data Converter
========================

Simple JSON to PostgresSQL data converter for bus stops.

organization: next-level-dev-pl
project: smart-route
url: https://github.com/next-level-dev-pl/smart-route/
autor: Dawid Bielecki ('dawciobiel')
data: 2025-02-28
version: 1.0.0-alpha


Usage:
1. Run the importer:
   python3 bus-stops-json-to-sql-converter.py

"""


import json
import sys
import os

def convert_json_to_sql(input_file, output_file):
    try:
        # Load JSON data from file
        with open(input_file, 'r', encoding='utf-8') as f:
            data = json.load(f)
        
        # Generate SQL file
        with open(output_file, 'w', encoding='utf-8') as f:
            # Create table structure
            f.write("""-- Creating bus stops table
CREATE TABLE IF NOT EXISTS bus_stops (
    zespol TEXT,
    slupek TEXT,
    nazwa_zespolu TEXT,
    id_ulicy TEXT,
    szer_geo NUMERIC,
    dlug_geo NUMERIC,
    kierunek TEXT,
    obowiazuje_od TIMESTAMP
);

-- Inserting data into the bus stops table
""")
            
            # Generate INSERT commands for each record
            if 'result' in data:
                for item in data['result']:
                    if 'values' in item:
                        # Transform list of values into dictionary for easier access
                        values_dict = {val['key']: val['value'] for val in item['values'] if 'key' in val and 'value' in val}
                        
                        # Check if we have all needed keys
                        keys = ['zespol', 'slupek', 'nazwa_zespolu', 'id_ulicy', 'szer_geo', 'dlug_geo', 'kierunek', 'obowiazuje_od']
                        
                        # Generate INSERT command
                        insert_values = []
                        for key in keys:
                            value = values_dict.get(key, '')
                            
                            # Handle different data types
                            if key in ['szer_geo', 'dlug_geo']:
                                # Check if value is a number
                                try:
                                    float(value)
                                    insert_values.append(value)
                                except (ValueError, TypeError):
                                    insert_values.append('NULL')
                            else:
                                # Escape apostrophes in text values
                                insert_values.append(f"'{value.replace('\'', '\'\'')}'")
                        
                        # Generate INSERT statement
                        insert_statement = f"""INSERT INTO bus_stops (zespol, slupek, nazwa_zespolu, id_ulicy, szer_geo, dlug_geo, kierunek, obowiazuje_od)
VALUES ({', '.join(insert_values)});
"""
                        f.write(insert_statement)
            
            print(f"Successfully generated SQL file: {output_file}")
            return True
    except Exception as e:
        print(f"An error occurred: {e}")
        return False

def main():
    # Check command line arguments
    if len(sys.argv) > 2:
        input_file = sys.argv[1]
        output_file = sys.argv[2]
    else:
        # Ask user for filenames if not provided as arguments
        input_file = input("Enter input JSON filename (e.g., bus-stops.json): ").strip()
        if not input_file:
            input_file = "bus-stops.json"
            
        output_file = input("Enter output SQL filename (e.g., bus-stops.sql): ").strip()
        if not output_file:
            # Create output filename based on input filename
            base_name = os.path.splitext(input_file)[0]
            output_file = f"{base_name}.sql"
    
    # Check if input file exists
    if not os.path.exists(input_file):
        print(f"Error: File {input_file} does not exist.")
        return
    
    # Convert file
    success = convert_json_to_sql(input_file, output_file)
    
    if success:
        print(f"Conversion completed successfully. Data has been saved to {output_file}")
        print(f"You can import the file to PostgreSQL using the command:")
        print(f"psql -U username -d database_name -f {output_file}")

if __name__ == "__main__":
    main()

