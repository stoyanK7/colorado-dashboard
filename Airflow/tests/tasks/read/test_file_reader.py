import os
import shutil
import unittest
from tasks.read.file_reader import FileReader


class TestFileReader(unittest.TestCase):
    def test_get_file_names_in_directory(self):
        test_files_dir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(test_files_dir, True)
        os.mkdir(test_files_dir)
        file_names = ["file1.txt", "file2.conf", "file3"]
        for fileName in file_names:
            open(test_files_dir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_file_names_in_directory(test_files_dir)

        self.assertListEqual(file_names, result)

        # Cleanup
        shutil.rmtree(test_files_dir, True)


    def test_get_sorted_file_names_in_directory(self):
        test_files_dir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(test_files_dir, True)
        os.mkdir(test_files_dir)
        file_names = ["cfile1.txt", "bfile2.conf", "afile3"]
        for fileName in file_names:
            open(test_files_dir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_sorted_file_names_in_directory(test_files_dir)

        self.assertListEqual(["afile3", "bfile2.conf", "cfile1.txt"], result)

        # Cleanup
        shutil.rmtree(test_files_dir, True)


    def test_get_file_names_starting_from(self):
        test_files_dir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(test_files_dir, True)

        os.mkdir(test_files_dir)
        file_names = ["file1.txt", "file2.conf", "file3"]
        for fileName in file_names:
            open(test_files_dir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_file_names_starting_from(test_files_dir, "file2.conf")

        self.assertListEqual(["file2.conf", "file3"], result)

        # Cleanup
        shutil.rmtree(test_files_dir, True)
