/*
import java.lang.Object;
import android.content.Intent;
import android.view.View;
import java.net.URI;
import android.graphics.Bitmap;
import java.io.InputStream;
*/


/**             **NOTE**:commented out until file is completely finished
 * Started one way to implement adding profile picture to profile
 * Uses Android Intent
 *      Android Intent is the message that is passed between components such as activities,
 *      content providers, broadcast receivers, services, etc.
 *      More info: https://www.javatpoint.com/android-intent-tutorial
 */
/*
public class UploadPicture {


    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
   photoPickerIntent.setType("image/*");

    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
                //Toast = displays info for a short period of time
                Toast.makeText(PostImage.this, "Error occurred", Toast.LENGTH_LONG).show();

            }

        } else {

            Toast.makeText(PostImage.this, "No image picked", Toast.LENGTH_LONG).show();

        }
    }



}
*/

