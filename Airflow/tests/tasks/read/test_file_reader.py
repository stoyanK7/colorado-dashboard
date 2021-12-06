import os
import shutil
import unittest
from tasks.read.file_reader import FileReader


class TestFileReader(unittest.TestCase):
    def test_get_file_names_in_directory(self):
        testFilesDir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(testFilesDir, True)
        os.mkdir(testFilesDir)
        fileNames = ["file1.txt", "file2.conf", "file3"]
        for fileName in fileNames:
            open(testFilesDir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_file_names_in_directory(testFilesDir)

        self.assertListEqual(fileNames, result)

        # Cleanup
        shutil.rmtree(testFilesDir, True)


    def test_get_sorted_file_names_in_directory(self):
        testFilesDir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(testFilesDir, True)
        os.mkdir(testFilesDir)
        fileNames = ["cfile1.txt", "bfile2.conf", "afile3"]
        for fileName in fileNames:
            open(testFilesDir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_sorted_file_names_in_directory(testFilesDir)

        self.assertListEqual(["afile3", "bfile2.conf", "cfile1.txt"], result)

        # Cleanup
        shutil.rmtree(testFilesDir, True)


    def test_get_file_names_starting_from(self):
        testFilesDir = os.getenv("AIRFLOW_HOME") + "/tests/data/"
        # pre-Cleanup
        shutil.rmtree(testFilesDir, True)

        os.mkdir(testFilesDir)
        fileNames = ["file1.txt", "file2.conf", "file3"]
        for fileName in fileNames:
            open(testFilesDir + fileName, "x").close()
        fr = FileReader()

        result = fr.get_file_names_starting_from(testFilesDir, "file2.conf")

        self.assertListEqual(["file2.conf", "file3"], result)

        # Cleanup
        shutil.rmtree(testFilesDir, True)
