package com.tencent.weibo.sdk.android.api.util;

import java.util.Comparator;


import com.tencent.weibo.sdk.android.model.Firend;

public class FirendCompare implements Comparator<Firend>{

	@Override
	public int compare(Firend lhs, Firend rhs) {
		return HypyUtil.cn2py(lhs.getName()).substring(0, 1).toUpperCase().compareTo(HypyUtil.cn2py(rhs.getName()).substring(0, 1).toUpperCase());
	}
 
}
