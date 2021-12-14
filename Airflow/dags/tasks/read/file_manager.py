import filecmp
import os
import shutil


class FileManager:

    def copy_files_to_dir(self, source_dir, target_dir, extension_to_folder):
        for machine_id in os.listdir(source_dir):
            shutil.copytree(source_dir + "/" + machine_id + extension_to_folder, target_dir + "/" + machine_id + extension_to_folder)