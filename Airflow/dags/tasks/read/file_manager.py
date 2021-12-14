import filecmp
import os
import shutil


class FileManager:

    def copy_files_to_dir(self, source_dir, target_dir, extension_to_folder):
        shutil.copytree(source_dir + extension_to_folder, target_dir + extension_to_folder)

if __name__ == '__main__':
    file_manager = FileManager()
    # file_manager.copy_files_to_dir("C:/Users/elibr/Documents/Fontys/GPS/Data",
    #                                "C:/Users/elibr/Documents/Fontys/GPS/DataCopy")
    print(file_manager.get_different_files("C:/Users/elibr/Documents/Fontys/GPS/DataCopy", "C:/Users/elibr/Documents/Fontys/GPS/Data", "/engineControl/accounting/image"))