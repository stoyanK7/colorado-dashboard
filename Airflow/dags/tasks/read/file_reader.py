import filecmp
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

    def get_file_names_starting_from(self, path, starting_file_name):
        file_names = self.get_file_names_in_directory(path)
        for fileIndex in range(len(file_names)):
            if (file_names[fileIndex]==starting_file_name):
                return file_names[fileIndex:len(file_names)]
        return file_names

    def read_pandas_csv_file(self, path, delimiter):
        return pd.read_csv(path, delimiter=delimiter)

    def get_different_files(self, new_dir, old_dir, path_to_files):
        new_files = {}
        changed_files = {}
        # for every machine id
        for machine_id in os.listdir(new_dir):
            # make empty lists
            new_files[machine_id] = []
            changed_files[machine_id] = []
            for root, dirs, files in os.walk(new_dir + "/" + machine_id + path_to_files):
                # for every file in the new files directory
                for file in files:
                    # if the file doesn't exist in the old directory, add it to the new_files list
                    if not os.path.isfile(old_dir + "/" + machine_id + path_to_files + '/' + file):
                        new_files[machine_id].append(new_dir + "/" + machine_id + path_to_files + '/' + file)

                    # else if it does exist, check if it is different. If so, add it to the changed_files list
                    elif not filecmp.cmp(new_dir + "/" + machine_id + path_to_files + '/' + file, old_dir + "/" + machine_id + path_to_files + '/' + file):
                        changed_files[machine_id].append({"new": new_dir + "/" + machine_id + path_to_files + '/' + file,
                                                         "old":old_dir + "/" + machine_id + path_to_files + '/' + file})

        return new_files, changed_files