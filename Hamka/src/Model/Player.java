package Model;

public class Player {
	private static int Serial = 0;
	private int id;
	private String username;
	private  int score;


	@Override
	public String toString() {
		return "username=" + username + ", score=" + score ;
	}

	public Player(String username) {
		super();
		this.username = username;
		id=++Serial;
		score=0;
	}

	//----------------Getters and Setters------------------
	public static int getSerial() {
		return Serial;
	}

	public static void setSerial(int serial) {
		Serial = serial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
	}
	
	public void updateScore(int scoreToAdd) {
		score += scoreToAdd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	//----------------------------------------------
}
