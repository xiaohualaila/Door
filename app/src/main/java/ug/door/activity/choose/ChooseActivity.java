package ug.door.activity.choose;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import ug.door.R;
import ug.door.Util.UtilToast;
import ug.door.activity.MainActivity;
import ug.door.activity.base.BaseAppCompatActivity;
import ug.door.activity.setUp.SetUpActivity;

/**
 * Created by ThinkPad on 2017/10/18.
 */

public class ChooseActivity  extends BaseAppCompatActivity {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_finish)
    TextView tv_finish;
    @Bind(R.id.checkBox1)
    CheckBox checkBox1;
    @Bind(R.id.checkBox2)
    CheckBox checkBox2;
    private boolean isChecked1 = false;
    private boolean isChecked2 = false;
    private boolean isCommonCheck= false;
    @Override
    protected void init() {
        tv_title.setText("设置设备");
        tv_finish.setVisibility(View.VISIBLE);
        initOnClickBox();


    }

    private void initOnClickBox() {
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isChecked1 = true;
                }else{
                    isChecked1 = false;
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isChecked2 = true;
                }else{
                    isChecked2 = false;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @OnClick({R.id.tv_finish,R.id.btn_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_finish:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                SetUpActivity.instance.finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
