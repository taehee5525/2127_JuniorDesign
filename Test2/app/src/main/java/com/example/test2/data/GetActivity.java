/** **NOTE**: class commented out until fully finished
 * This class uses the Activity Result APIs introduced in AndroidX Activity and Fragment
 *
 * more info/documentation: https://developer.android.com/training/basics/intents/result#java
 *
 * The Activity Result APIs provide components for registering for a result, launching the result,
 * and handling the result once it is dispatched by the system.
 *
 * Purpose of this class is to help perform activities like allowingg app to start the Camera
 * app and receieve the captured photo as a result, or start the Contacts app and allow the user
 * to select a contact and you'll receive the contact details as a result
 *
 */

/*
import androidx.activity.*;
import androidx.fragment.*;
import java.net.URI;
/**
* This class uses the Activity Result APIs introduced in AndroidX Activity and Fragment
*
* more info/documentation: https://developer.android.com/training/basics/intents/result#java
*
* The Activity Result APIs provide components for registering for a result, launching the result,
* and handling the result once it is dispatched by the system.
*
* Purpose of this class is to help perform activities like allowingg app to start the Camera
* app and receieve the captured photo as a result, or start the Contacts app and allow the user
* to select a contact and you'll receive the contact details as a result
*
*/



/*
public class GetActivity {

    // GetContent creates an ActivityResultLauncher<String> to allow you to pass
    // in the mime type you'd like to allow the user to select
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new GetContent(),
            new ActivityResultCallback<Uri>() {

                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                }
            });

    public void onCreate(@Nullable Bundle savedInstanceState) {

        Button selectButton = findViewByID(R.pathtobutton);

        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass in mime type you want user to select as the input
                mGetContent.launch("image/*");
            }

        });

        @Override
        void onCreate(Bundle savedInstanceState) {

            mObserver = new MyLifecycleObserver(requireActivity().getActivityResultRegistry());
            getLifecycle().addObserver(mObserver);
        }

        @Override
        void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            Button selectButton = findViewById(R./*path*//*);
            selectButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mObserver.selectImage();
                }

            }
        }
    }
}
*/
