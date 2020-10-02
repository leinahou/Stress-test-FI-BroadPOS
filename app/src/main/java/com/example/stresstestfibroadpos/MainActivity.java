package com.example.stresstestfibroadpos;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.pax.poslink.CommSetting;
import com.pax.poslink.aidl.BasePOSLinkCallback;
import com.pax.poslink.fullIntegration.InputAccount;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public InputAccount.InputAccountRequest doInputAccount(){
        InputAccount.InputAccountRequest inputreq = new InputAccount.InputAccountRequest();

        inputreq.setEdcType("CREDIT");
        inputreq.setTransType("SALE");
        inputreq.setAmount("100");
        inputreq.setMagneticSwipeEntryFlag("1");
        inputreq.setManualEntryFlag("1");
        inputreq.setContactlessEntryFlag("1");
        inputreq.setContactEMVEntryFlag("1");
        inputreq.setEncryptionFlag("1");
        inputreq.setKeySLot("1");
        inputreq.setTimeOut("300");

        return inputreq;
    }

    public void processtrans(View view) {
        EditText times = findViewById(R.id.editTextTextPersonName);
        int iterr = Integer.parseInt(times.getText().toString());

        for (int i = 0; i < iterr; i++) {
            try{
                InputAccount.inputAccountWithEMV(this, doInputAccount(), commset(), new BasePOSLinkCallback<InputAccount.InputAccountResponse>() {
                    @Override
                    public void onFinish(InputAccount.InputAccountResponse inputAccountResponse) {
                        Log.i("Result code", inputAccountResponse.getResultCode());
                        Log.i("Result text", inputAccountResponse.getResultTxt());
                    }
                }, new InputAccount.InputAccountCallback() {
                    @Override
                    public void onInputAccountStart() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        InputAccount.getInstance().abort();
                    }

                    @Override
                    public void onEnterExpiryDate() {

                    }

                    @Override
                    public void onEnterZip() {

                    }

                    @Override
                    public void onEnterCVV() {

                    }

                    @Override
                    public void onSelectEMVApp(List<String> list) {

                    }

                    @Override
                    public void onProcessing(String s, String s1) {

                    }

                    @Override
                    public void onWarnRemoveCard() {

                    }
                });
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}