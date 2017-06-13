import java.time.LocalTime;
import java.util.Date;

public class ServiceDesk {
	public int openT;
	public int closeT;
	public int breakTotal;
	public int serviceT;
	public ServiceDesk(){
		this.setOpenT(32400);
		this.setCloseT(61200);// seconds from 9-5pm
		this.setBreakTotal(61200);//consider the object's break begins as a full day 9-5p
	}

	public int getBreakTotal() {
		return breakTotal;
	}
	public void setBreakTotal(int breakTotal) {
		this.breakTotal = breakTotal;
	}
	public int getCloseT() {
		return closeT;
	}
	public void setCloseT(int closeT) {
		this.closeT = closeT;
	}

	public int getOpenT() {
		return openT;
	}

	public void setOpenT(int openT) {
		this.openT = openT;
	}
	
	public void setServiceT(int serviceT){
		this.serviceT=serviceT;
	}
}
