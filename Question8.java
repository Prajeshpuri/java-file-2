// program to demonstrate inter-thread communication

class Customer {
	int amount = 10000;
	synchronized void Withdraw (int amount) {
		System.out.println ("Balance = " + this.amount);
		System.out.println ("Going to withdraw " + amount + "...");
		if (this.amount < amount) {
			System.out.println ("Less balance! Waiting for deposit...");
			try {
				wait ();
			}
			catch (Exception e) {}
		}
		this.amount -= amount;
		System.out.println ("Withdraw completed. Balance = " + this.amount);
	}
	synchronized void Deposit (int amount) {
		System.out.println ("Going to deposit " + amount + "...");
		this.amount += amount;
		System.out.println ("Deposit completed...");
		notify ();
	}
}

class MyThread1 extends Thread {
	Customer c;
	MyThread1 (Customer c) {
		this.c = c;
	}
	public void run () {
		c.Withdraw (15000);
	}
}

class MyThread2 extends Thread {
	Customer c;
	MyThread2 (Customer c) {
		this.c = c;
	}
	public void run () {
		c.Deposit (10000);
	}
}

class Question8 {
	public static void main (String args[]) {
		final Customer c = new Customer ();
		MyThread1 t1 = new MyThread1 (c);
		MyThread2 t2 = new MyThread2 (c);
		t1.start ();
		t2.start ();
	}
}