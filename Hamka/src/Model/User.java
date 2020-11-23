package Model;

import java.io.Serializable;

import Utils.E_UserType;

/**
 * This class represents a user of the game.(Regular player OR Admin).
 */
public class User implements Serializable{
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	/**default serial version ID*/
	private static final long serialVersionUID = 1L;
	
	/**user's nickname*/
	private String nickName;

	/**user's type*/
	private E_UserType userType;
	
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	public User(String nickname) {
		this.nickName = nickname;
	}
	
	public User(String nickName, E_UserType userType) {
		this.nickName = nickName;
		this.userType = userType;
		
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------
	//----------------------------getters & setters----------------------
	//-------------------------------------------------------------------
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public E_UserType getUserType() {
		return userType;
	}

	public void setUserType(E_UserType userType) {
		this.userType = userType;
	}
	
	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
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
		User other = (User) obj;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		return true;
	}
}
