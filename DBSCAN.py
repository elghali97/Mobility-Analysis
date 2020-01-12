import math
import pandas as pd
from datetime import timedelta, datetime
from geopy.distance import great_circle
"""
INPUTS:
    df={o1,o2,...,on} Set of objects
    spatial_threshold = Maximum geographical coordinate (spatial) distance value
    temporal_threshold = Maximum non-spatial distance value
    min_neighbors = Minimun number of points within Eps1 and Eps2 distance
OUTPUT:
    C = {c1,c2,...,ck} Set of clusters
"""
def ST_DBSCAN(df, spatial_threshold = 50, temporal_threshold = 10, min_neighbors = 1):
    cluster_label = 0
    NOISE = -1
    UNMARKED = 777777
    stack = []

    # initialize each point with unmarked
    df['cluster'] = UNMARKED
    
    # for each point in database
    for index, point in df.iterrows():
        if df.loc[index]['cluster'] == UNMARKED:
            neighborhood = retrieve_neighbors(index, df, spatial_threshold, temporal_threshold)
            
            if len(neighborhood) < min_neighbors:
                df.set_value(index, 'cluster', NOISE)

            else: # found a core point
                cluster_label = cluster_label + 1
                df.set_value(index, 'cluster', cluster_label)# assign a label to core point

                for neig_index in neighborhood: # assign core's label to its neighborhood
                    df.set_value(neig_index, 'cluster', cluster_label)
                    stack.append(neig_index) # append neighborhood to stack
                
                while len(stack) > 0: # find new neighbors from core point neighborhood
                    current_point_index = stack.pop()
                    new_neighborhood = retrieve_neighbors(current_point_index, df, spatial_threshold, temporal_threshold)
                    
                    if len(new_neighborhood) >= min_neighbors: # current_point is a new core
                        for neig_index in new_neighborhood:
                            neig_cluster = df.iloc[neig_index]['cluster']
                            if (neig_cluster != NOISE) & (neig_cluster == UNMARKED): 
                                # TODO: verify cluster average before add new point
                                df.set_value(neig_index, 'cluster', cluster_label)
                                stack.append(neig_index)
    return df


def retrieve_neighbors(index_center, df, spatial_threshold, temporal_threshold):
    neigborhood = []

    center_point = df.loc[index_center]
    # filter by time 
    min_time = str(datetime.strptime(str(center_point['datetime']), '%Y-%m-%d %H:%M:%S.%f') - timedelta(minutes = temporal_threshold))
    max_time = str(datetime.strptime(str(center_point['datetime']), '%Y-%m-%d %H:%M:%S.%f') + timedelta(minutes = temporal_threshold))
    df = df[(df['datetime'] >= min_time) & (df['datetime'] <= max_time)]
    # filter by distance
    for index, point in df.iterrows():
        if index != index_center:
            distance = great_circle((center_point['lat'], center_point['lng']), (point['lat'], point['lng'])).meters
            if distance <= spatial_threshold:
                neigborhood.append(index)

    return neigborhood