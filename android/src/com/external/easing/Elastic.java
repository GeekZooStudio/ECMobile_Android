package com.external.easing;
 
public class Elastic {

	public static float  easeIn(float t,float b , float c, float d ) {
		if (t==0) return b;  if ((t/=d)==1) return b+c;  
		float p=d*.3f;
		float a=c; 
		float s=p/4;
		return -(a*(float)Math.pow(2,10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p )) + b;
	}

	public static float  easeIn(float t,float b , float c, float d, float a, float p) {
		float s;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  
		if (a < Math.abs(c)) { a=c;  s=p/4; }
		else { s = p/(2*(float)Math.PI) * (float)Math.asin (c/a);}
		return -(a*(float)Math.pow(2,10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	}

	public static float  easeOut(float t,float b , float c, float d) {
		if (t==0) return b;  if ((t/=d)==1) return b+c;  
		float p=d*.3f;
		float a=c; 
		float s=p/4;
		return (a*(float)Math.pow(2,-10*t) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p ) + c + b);	
	}
	
	public static float  easeOut(float t,float b , float c, float d, float a, float p) {
		float s;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  
		if (a < Math.abs(c)) { a=c;  s=p/4; }
		else { s = p/(2*(float)Math.PI) * (float)Math.asin (c/a);}
		return (a*(float)Math.pow(2,-10*t) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p ) + c + b);	
	}
	
	public static float  easeInOut(float t,float b , float c, float d) {
		if (t==0) return b;  if ((t/=d/2)==2) return b+c; 
		float p=d*(.3f*1.5f);
		float a=c; 
		float s=p/4;
		if (t < 1) return -.5f*(a*(float)Math.pow(2,10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p )) + b;
		return a*(float)Math.pow(2,-10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p )*.5f + c + b;
	}
	
	public static float  easeInOut(float t,float b , float c, float d, float a, float p) {
		float s;
		if (t==0) return b;  if ((t/=d/2)==2) return b+c;  
		if (a < Math.abs(c)) { a=c; s=p/4; }
		else { s = p/(2*(float)Math.PI) * (float)Math.asin (c/a);}
		if (t < 1) return -.5f*(a*(float)Math.pow(2,10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p )) + b;
		return a*(float)Math.pow(2,-10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p )*.5f + c + b;
	}

}
