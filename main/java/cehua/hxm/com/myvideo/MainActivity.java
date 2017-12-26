package cehua.hxm.com.myvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button start_bn;
    private EditText ip;
    private EditText port;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_bn=(Button) findViewById(R.id.button);
        img=(ImageView)findViewById(R.id.imageView);
        ip=(EditText)findViewById(R.id.editText);
        port=(EditText)findViewById(R.id.editText2);
        start_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Socket_Client(ip.getText().toString(),Integer.parseInt(port.getText().toString()),img);
          }
        });

    }

}
