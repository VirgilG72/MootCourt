package com.mob.ums.gui.themes.defaultt;


import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.mob.ums.gui.pages.VcodeLoginPage;
import com.mob.ums.gui.pages.dialog.ErrorDialog;
import com.mob.ums.gui.pages.dialog.OKDialog;
import com.mob.ums.gui.pages.dialog.ProgressDialog;
import com.mob.ums.gui.themes.defaultt.components.PhoneView;
import com.mob.ums.gui.themes.defaultt.components.TitleView;
import com.mob.ums.gui.themes.defaultt.components.VCodeView;

import java.util.HashMap;

public class VcodeLoginPageAdapter extends DefaultThemePageAdapter<VcodeLoginPage> {
	private VCodeView llVCode;
	private TextView tvCountDown;
	private ProgressDialog pd;

	@Override
	public void onCreate(VcodeLoginPage page, Activity activity) {
		super.onCreate(page, activity);
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		initPage(activity);
	}

	private void initPage(final Activity activity) {
		final LinearLayout llPage = new LinearLayout(activity);
		llPage.setOrientation(LinearLayout.VERTICAL);
		activity.setContentView(llPage);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		llPage.addView(new TitleView(activity) {
			protected void onReturn() {
				finish();
			}

			protected String getTitleResName() {
				return null;
			}
		}, lp);

		LinearLayout llBody = new LinearLayout(activity);
		llBody.setOrientation(LinearLayout.VERTICAL);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
				.WRAP_CONTENT);
		lp.weight = 1;
		int dp15 = ResHelper.dipToPx(activity, 15);
		lp.leftMargin = dp15;
		lp.rightMargin = dp15;
		llPage.addView(llBody, lp);

		final TextView tvTip = new TextView(activity);
		tvTip.setText(ResHelper.getStringRes(activity, "umssdk_default_input_phone"));
		tvTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tvTip.setTextColor(0xff000000);
		tvTip.setGravity(Gravity.CENTER_HORIZONTAL);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
				.WRAP_CONTENT);
		int dp30 = ResHelper.dipToPx(activity, 30);
		int dp40 = ResHelper.dipToPx(activity, 40);
		lp.topMargin = dp30;
		lp.bottomMargin = dp40;
		llBody.addView(tvTip, lp);

		final PhoneView llPhone = new PhoneView(getPage().getTheme()){
			@Override
			protected void changePhone(String change) {
				if(TextUtils.isEmpty(change)){
					tvTip.setText(ResHelper.getStringRes(activity, "umssdk_default_input_phone"));
				}
			}
		};
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
				.WRAP_CONTENT);
		llBody.addView(llPhone, lp);
		llPhone.setPhone(getPage().getLoginNumber());

		llVCode = new VCodeView(getPage()) {
			protected String getContry() {
				return llPhone.getCountry();
			}

			protected String getPhone() {
				return llPhone.getPhone();
			}

			protected boolean beforeSend() {
				if(TextUtils.isEmpty(getPhone()) || getPhone().length()<=0){
					Toast.makeText(getContext(), ResHelper.getStringRes(getContext(),"umssdk_default_phone_tip"),Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}

			protected boolean isRegiterType() {
				return false;
			}

			@Override
			protected boolean noCheckExist() {
				return true;
			}

			protected void afterSend(boolean smartVCode) {
				if (smartVCode) {
					OKDialog.Builder builder = new OKDialog.Builder(activity, getPage().getTheme());
					int resId = ResHelper.getStringRes(getContext(), "umssdk_default_send_vcode");
					builder.setTitle(getContext().getString(resId));
					resId = ResHelper.getStringRes(getContext(), "umssdk_default_smart_verified");
					builder.setMessage(getContext().getString(resId));
					builder.show();
				} else {
					tvCountDown.setVisibility(VISIBLE);
					tvTip.setText(ResHelper.getStringRes(activity, "umssdk_default_vcode_sended"));
				}
			}

			protected void onCountDown(int secondsLeft) {
				if (secondsLeft > 0) {
					int resId = ResHelper.getStringRes(activity, "umssdk_default_x_seconds_to_receive_vcode");
					String msg = activity.getString(resId, secondsLeft);
					tvCountDown.setTextColor(0xff979797);
					tvCountDown.setText(msg);
					tvCountDown.setOnClickListener(null);
				} else {
					stopCountDown();
					tvCountDown.setTextColor(0xffe4554c);
					tvCountDown.setText(ResHelper.getStringRes(activity, "umssdk_default_can_not_receive_vcode"));
					tvCountDown.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							askToSend();
						}
					});
				}
			}
		};
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
				.WRAP_CONTENT);
		llBody.addView(llVCode, lp);

		tvCountDown = new TextView(activity);
		tvCountDown.setTextColor(0xff979797);
		tvCountDown.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		tvCountDown.setGravity(Gravity.CENTER);
		tvCountDown.setVisibility(View.INVISIBLE);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
				.WRAP_CONTENT);
		int dp7 = ResHelper.dipToPx(activity, 7);
		lp.topMargin = dp7;
		llBody.addView(tvCountDown, lp);
		tvCountDown.setVisibility(View.INVISIBLE);

		// action button
		TextView tv = new TextView(activity);
		tv.setBackgroundResource(ResHelper.getBitmapRes(activity, "umssdk_default_login_btn"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		tv.setTextColor(0xffffffff);
		tv.setText(ResHelper.getStringRes(activity, "umssdk_default_login"));
		tv.setGravity(Gravity.CENTER);
		tv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				login(llPhone.getCountry(), llPhone.getPhone(), llVCode.getVCode());
			}
		});
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ResHelper.dipToPx(activity, 45));
		lp.topMargin = dp40;
		llBody.addView(tv, lp);
	}

	private void login(String country, String phone, String smsCode) {
		if (TextUtils.isEmpty(phone) || phone.length() <= 0) {
			Toast.makeText(getPage().getContext(), ResHelper.getStringRes(getPage().getContext(),
					"umssdk_default_phone_tip"),Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(smsCode) || smsCode.length() <= 0) {
			Toast.makeText(getPage().getContext(), ResHelper.getStringRes(getPage().getContext(),
					"umssdk_default_vcode_tip"),Toast.LENGTH_SHORT).show();
			return;
		}
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		pd = new ProgressDialog.Builder(getPage().getContext(), getPage().getTheme()).show();
		UMSSDK.loginWithPhoneVCode(country, phone, smsCode, DeviceHelper.getInstance(getPage().getContext())
				.getSimSerialNumber(), new OperationCallback<User>() {
			@Override
			public void onSuccess(User data) {
				super.onSuccess(data);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				HashMap<String, Object> res = new HashMap<String, Object>();
				res.put("me", data);
				getPage().setResult(res);
				getPage().finish();
			}

			@Override
			public void onFailed(Throwable t) {
				super.onFailed(t);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				ErrorDialog.Builder builder = new ErrorDialog.Builder(getPage().getContext(), getPage().getTheme());
				int resId = ResHelper.getStringRes(getPage().getContext(), "umssdk_default_login");
				builder.setTitle(getPage().getContext().getString(resId));
				builder.setThrowable(t);
				builder.setMessage(t.getMessage());
				builder.show();
			}
		});
	}
}
