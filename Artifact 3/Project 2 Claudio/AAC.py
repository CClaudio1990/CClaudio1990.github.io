import json
from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, config_path='credentials.json'):
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #

        # Adding file loading capability to AAC
        try:
            with open(config_path, 'r') as file:
                config = json.load(file)
        except Exception as e:
            raise Exception(f"Failed to load credential: {e}")

        # Credentials are now passed from a separate file to avoid publishing sensitive data in code
        USER = config.get('username')
        PASS = config.get('password')
        HOST = config.get('host')
        PORT = config.get('port')
        DB = config.get('database')
        COL = config.get('collection')
        #
        # Initialize Connection
        #
        self.client = MongoClient(f'mongodb://{USER}:{PASS}@{HOST}:{PORT}')
        self.database = self.client[DB]
        self.collection = self.database[COL]

# Complete this create method to implement the C in CRUD.
    """Inserts a new document into the animals collection.
        
        Args:
            data (dict): Document to insert. Cannot be an empty dictionary.
        
        Returns:
            bool: True if the insertion was successful, False otherwise.
        
        Raises:
            Exception: If the data parameter is None or empty.
    """
    def create(self, data):
        if data is not None:
            result = self.collection.insert_one(data) # data should be dictionary
            return True if result.inserted_id else False
        else:
            raise Exception("Nothing to save, because data parameter is empty")

# Create method to implement the R in CRUD.
    """Retrieves documents from the animals collection based on the provided query.
        
        Args:
            query (dict): The query filter to match documents.
        
        Returns:
            list: A list of matching documents. Returns an empty list if no documents are found
                 or if an error occurs.
    """
    def read(self, query):
        try:
            documents = list(self.collection.find(query))
            return documents if documents else []
        except Exception as e:
            print(f"Error Reading DB: {e}")
            return []
            
# Create method to implement the U in CRUD.
    """Updates documents in the animals collection that match the query.
        
        Args:
            query (dict): The query filter to match documents.
            update_data (dict): The update operations to apply to matched documents.
        
        Returns:
            int: The number of documents modified. Returns 0 if an error occurs.
    """
    def update(self, query, update_data):
        try:
            result = self.collection.update_many(query, {"$set": update_data})
            return result.modified_count
        except Exception as e:
            print(f"Error Updating DB: {e}")
            return 0

    # Create method to implement the D in CRUD.
    """Deletes documents from the animals collection that match the query.
        
        Args:
            query (dict): The query filter to match documents.
        
        Returns:
            int: The number of documents deleted. Returns 0 if an error occurs.
    """
    def delete(self, query):
        try:
            result = self.collection.delete_many(query)
            return result.modified_count
        except Exception as e:
            print(f"Error Deleting from DB: {e}")
            return 0