### Script in python which prints the project strcuture all the files and folders available

import os

def print_tree(startpath, exclude_dirs, exclude_files):
    for root, dirs, files in os.walk(startpath):
        # Filter out unwanted directories
        dirs[:] = [d for d in dirs if d not in exclude_dirs and not d.startswith('.')]
        
        level = root.replace(startpath, '').count(os.sep)
        indent = ' ' * 4 * (level)
        print(f'{indent}{os.path.basename(root)}/')
        
        sub_indent = ' ' * 4 * (level + 1)
        for f in files:
            if f not in exclude_files and not f.startswith('.'):
                print(f'{sub_indent}{f}')

if __name__ == "__main__":
    # Define what to ignore
    ignored_folders = {
        'node_modules', 'build', 'dist',           # Frontend
        'target', '.settings', '.metadata', 'bin', # Backend/Java
        '.git', '.idea', '.vscode', 'venv'         # Config/Git
    }
    
    ignored_files = {
        '.DS_Store', 'package-lock.json', 'yarn.lock', 
        'mvnw', 'mvnw.cmd', 'pom.xml.tag'
    }

    root_dir = os.getcwd()
    print(f"Project Structure for: {os.path.basename(root_dir)}")
    print("=" * 40)
    print_tree(root_dir, ignored_folders, ignored_files)