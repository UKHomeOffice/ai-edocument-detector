from pathlib import Path
import imghdr
import os

data_dir = os.environ['TEST_SET_ROOT']
#data_dir = os.environ['TRAINING_SET_ROOT']

image_extensions = [".png", ".jpg"]

img_type_accepted_by_tf = ["bmp", "gif", "jpg", "jpeg", "png"]
for filepath in Path(data_dir).rglob("*"):
    if filepath.suffix.lower() in image_extensions:
        img_type = imghdr.what(filepath)
        if img_type is None:
            print(f"{filepath} is not an image")
        elif img_type not in img_type_accepted_by_tf:
            print(f"{filepath} is a {img_type}, not accepted by TensorFlow")

print("no bad files found")
