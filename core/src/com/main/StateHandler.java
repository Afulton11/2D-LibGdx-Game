package com.main;

public class StateHandler {
	
	private static State currentState;
	
	public StateHandler(State startState) {
		currentState = startState;
	}
	
	/**
	 * returns the current state the game is at.
	 * @return the current state the game is at.
	 */
	public State getState() {
		return currentState;
	}
	
	/**
	 * Sets the current game state to the loading state.
	 */
	public void setStateLoading() {
		currentState = State.LOADING;
	}
	
	/**
	 * Sets the current game state to the Main_Menu state.
	 */
	public void setStateMainMenu() {
		currentState = State.MAIN_MENU;
	}
	
	/**
	 * Sets the current game state to the "Game" state.
	 */
	public void setStateGame() {
		currentState = State.GAME;
	}
	
}
