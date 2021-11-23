import os
import pandas as pd
import numpy as np

class FileReader():
    def getFileNamesInDirectory(self, path):
        for (root, dirs, files) in os.walk(path):
            return files

    def getSortedFileNamesInDirectory(self, path):
        return sorted(self.getFileNamesInDirectory(path))

    def getFileNamesStartingFrom(self, path, startingFileName):
        fileNames = self.getFileNamesInDirectory(path)
        for fileIndex in range(len(fileNames)):
            if (fileNames[fileIndex]==startingFileName):
                return fileNames[fileIndex:len(fileNames)]

    def readPandasCsvFile(self, path):
        return pd.read_csv(path);