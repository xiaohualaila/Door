package ug.door.activity.login;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ug.door.activity.base.BasePresenter;
import ug.door.retrofit.Api;
import ug.door.retrofit.ConnectUrl;


/**
 * Created by xyuxiao on 2016/9/26.
 */
public class LoginPresenter extends BasePresenter implements LoginContract.Presenter{
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void login(String username, String password) {
        view.loginErrorMsg("网络无法连接");
//        Api.getBaseApiWithOutFormat(ConnectUrl.URL)
//                .login(username,password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<JSONObject>() {
//                               @Override
//                               public void call(JSONObject jsonObject) {
//                                   view.loginErrorMsg("网络无法连接");
//                               }
//                           }
//                        , new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                view.loginErrorMsg("网络无法连接");
//                            }
//                        });
    }
}
