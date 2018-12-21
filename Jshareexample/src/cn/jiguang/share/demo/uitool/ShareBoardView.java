package cn.jiguang.share.demo.uitool;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;


/**
 * Created by cloud on 17/1/13.
 */

public class ShareBoardView extends PopupWindow {
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoardView(Context context, List<SnsPlatform> platforms) {
        this(context, platforms, null);
    }

    public ShareBoardView(Context context, List<SnsPlatform> platforms, ShareBoardConfig config) {
        super(context);
        this.setWindowLayoutMode(-1, -1);
        boolean isHorizontal = false;
        if(context.getResources().getConfiguration().orientation == 2) {
            isHorizontal = true;
        }

        if(config == null) {
            config = new ShareBoardConfig();
        }

        this.mShareBoardConfig = config;
        config.setOrientation(isHorizontal);
        JGActionFrame actionFrame = new JGActionFrame(context);
        actionFrame.setSnsPlatformData(platforms, config);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        actionFrame.setLayoutParams(params);
        actionFrame.setDismissListener(new OnDismissListener() {
            public void onDismiss() {
                ShareBoardView.this.dismiss();
                OnDismissListener onDismissListener = ShareBoardView.this.mShareBoardConfig != null?ShareBoardView.this.mShareBoardConfig.getOnDismissListener():null;
                if(onDismissListener != null) {
                    onDismissListener.onDismiss();
                }

            }
        });
        this.setContentView(actionFrame);
        this.setFocusable(true);
        this.saveShareboardConfig(context, config);
    }

    private void saveShareboardConfig(Context context, ShareBoardConfig config) {
        if(context != null && config != null) {
            String position = config.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM?"0":"1";
            String shape = null;
            if(config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_NONE) {
                shape = "0";
            } else if(config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_CIRCULAR) {
                shape = "1";
            } else if(config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE) {
                if(config.mMenuBgShapeAngle != 0) {
                    shape = "2";
                } else {
                    shape = "3";
                }
            }

        }
    }

    public void setShareBoardlistener(final ShareBoardlistener shareBoardlistener) {
        if(this.mShareBoardConfig != null) {
            ShareBoardlistener wrapListener = new ShareBoardlistener() {
                public void onclick(SnsPlatform snsPlatform, String share_media) {
                    ShareBoardView.this.dismiss();
                    if(shareBoardlistener != null) {
                        shareBoardlistener.onclick(snsPlatform, share_media);
                    }

                }
            };
            this.mShareBoardConfig.setShareBoardlistener(wrapListener);
        }
    }
}

