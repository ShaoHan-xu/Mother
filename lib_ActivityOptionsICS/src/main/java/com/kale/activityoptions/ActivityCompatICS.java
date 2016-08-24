package com.kale.activityoptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivityCompatICS {

    public static void startActivity(Activity activity, Intent intent, Bundle bundle, int requestCode) {
        if (bundle == null) {
            throw new RuntimeException("Bundle must be not null");
        }

        int animType = bundle.getInt(ActivityOptionsCompatICS.KEY_ANIM_TYPE, 0);
        int enterResId;
        int exitResId;
        if (animType == ActivityOptionsCompatICS.ANIM_CUSTOM) {
            enterResId = bundle.getInt(ActivityOptionsCompatICS.KEY_ANIM_ENTER_RES_ID);
            exitResId = bundle.getInt(ActivityOptionsCompatICS.KEY_ANIM_EXIT_RES_ID);
        } else {
            enterResId = 0;
            exitResId = 0;
        }

        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(enterResId, exitResId);
    }


}
