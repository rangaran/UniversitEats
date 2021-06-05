import pandas as pd
import json
import csv
from tqdm import tqdm

def parse_data(source1, output_path):
    # setup an array for writing each row in the csv file
    rows = []

    # extract fields from business json data set #
    # setup an array for storing each json entry
    business_data = []

    # setup an array for headers we are not using strictly
    business_header_removals = ['attributes', 'categories', 'hours', 'is_open']
    # setup an array for headers we are adding
    business_header_additions = ['Is_Restaurant', 'Sandwiches', 'Fast Food',
                                 'Pizza', 'Mexican', 'American (Traditional)',
                                 'Burgers', 'Chinese', 'Italian', 'American (New)', 'Breakfast & Brunch', 'Thai',
                                 'Indian', 'Sushi Bars', 'Korean', 'Mediterranean', 'Japanese', 'Seafood',
                                 'Middle Eastern', 'Pakistani', 'Barbeque', 'Vietnamese', 'Asian Fusion', 'Diners',
                                 'Greek', 'Vegetarian', 'RestaurantsPriceRange2'
                                ]
                            
    
    # open the business source file
    with open(source1) as f:
        # for each line in the json file
        for line in f:
            # store the line in the array for manipulation
            business_data.append(json.loads(line))
    # close the reader
    f.close()

    # append the initial keys as csv headers
    header = sorted(business_data[0].keys())
    print((business_data[0].keys()))

    # remove keys from the business data that we are not using strictly
    for headers in business_header_removals:
        header.remove(headers)
    # append the additional business related csv headers
    for headers in business_header_additions:
        header.append(headers)
    print('processing data in the business dataset...')

    for entry in tqdm(range(0, len(business_data))):
        row = []
        row.append(business_data[entry]['address'])
        row.append(business_data[entry]['business_id'])
        row.append(business_data[entry]['city'])
        row.append(business_data[entry]['latitude'])
        row.append(business_data[entry]['longitude'])
        row.append(business_data[entry]['name'])
        row.append(business_data[entry]['postal_code'])
        row.append(business_data[entry]['review_count'])
        row.append(business_data[entry]['stars'])
        row.append(business_data[entry]['state'])
        
        categories_of_interest = ['Restaurants', 'Sandwiches', 'Fast Food',
                                 'Pizza', 'Mexican', 'American (Traditional)',
                                 'Burgers', 'Chinese', 'Italian', 'American (New)', 'Breakfast & Brunch', 'Thai',
                                 'Indian', 'Sushi Bars', 'Korean', 'Mediterranean', 'Japanese', 'Seafood',
                                 'Middle Eastern', 'Pakistani', 'Barbeque', 'Vietnamese', 'Asian Fusion', 'Diners',
                                 'Greek', 'Vegetarian'
                                 ]
        

        
        #TESTING IF ATTRIBUTES FIELD EXISTS
        print(business_data[entry]['name'])
        print('attributes' in business_data[entry])
        
        # for each category of interest
        for category in categories_of_interest:
            try:
                if category in business_data[entry]['categories']:
                    row.append(1)
            except:
                row.append(0)
            else:
               row.append(0)
        
        if 'RestaurantsPriceRange2' in business_data[entry]['attributes']:
             try:
                 if business_data[entry]['attributes']['RestaurantsPriceRange2'] is not None:
                     row.append(business_data[entry]['attributes']['RestaurantsPriceRange2'])
             except:
                 row.append('NA')
        else:
             # append NA for the attribute
              row.append('NA')


        
        # this code taken from northwestern university's dataset cleanup
        row_clean = []
        for item in row:
            # scan and replace for nasty text
            row_clean.append(str(item).replace('\n', ' '))
         # after all fields have been extracted and cleaned, append the row to the rows array for writing to csv
        rows.append(row_clean)
        

    with open(output_path, 'w') as out:
        writer = csv.writer(out)
        writer.writerow(header)
        print('writing contents to csv...')
        for entry in tqdm(range(0, len(rows))):
            try:
                writer.writerow(rows[entry])
            except UnicodeEncodeError:
                continue
    out.close()

    print(header)

parse_data('Raw_Data/busprac.json', 'Prepped_Data/icecreampop.csv')