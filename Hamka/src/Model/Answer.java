package Model;
/**
 * This class represents a single answer option of a question in the game.
 */
public class Answer {
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	/**answer's content text*/
	private String text;
    
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	public Answer(String text){
		this.text=text;
	}
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------
	//----------------------------getters & setters----------------------
	//-------------------------------------------------------------------
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Answer other = (Answer) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
	
}
