import java.lang.Object;
import android.content.Intent;
import android.view.View;
import java.net.URI;
import android.graphics.Bitmap;
import java.io.InputStream;
//import


/**
 * Started one way to implement adding profile picture to profile
 * Uses Android Intent
 *      Android Intent is the message that is passed between components such as activities,
 *      content providers, broadcast receivers, services, etc.
 *      More info: https://www.javatpoint.com/android-intent-tutorial
 */

public class ProfilePicture {


    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
   photoPickerIntent.setType("image/*");

    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {


    }
}

}
