import logging
import os
import pandas as pd
import numpy as np

class FileReader():
    def get_file_names_in_directory(self, path):
        for (root, dirs, files) in os.walk(path):
            return files

    def get_sorted_file_names_in_directory(self, path):
        return sorted(self.get_file_names_in_directory(path))

    def get_file_names_starting_from(self, path, startingFileName):
        fileNames = self.get_file_names_in_directory(path)
        for fileIndex in range(len(fileNames)):
            if (fileNames[fileIndex]==startingFileName):
                return fileNames[fileIndex:len(fileNames)]
        return fileNames

    def read_pandas_csv_file(self, path, delimiter):
        return pd.read_csv(path, delimiter=delimiter)