package ug.door.activity.login;


import ug.door.activity.base.IBasePresenter;
import ug.door.activity.base.IBaseView;
import ug.door.model.User;

/**
 * Created by xyuxiao on 2016/9/26.
 */
public interface LoginContract {
    interface View extends IBaseView<Presenter> {

        void loginErrorMsg(String msg);
    }

    interface Presenter extends IBasePresenter {
        void login(String username, String password);

    }
}
