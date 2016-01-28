package cn.androiddevelop.cycleviewpager.lib;

import android.content.Context;
import android.os.Handler;

/**为了防止内存泄漏，定义外部类，防止内部类对外部类的引用*/

public class HotCampusActivityCycleViewPagerHandler extends Handler {
	 Context context;

	public HotCampusActivityCycleViewPagerHandler(Context context) {
		this.context = context;
	}
}