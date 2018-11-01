import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task_118 {
	public static void main(String[] args) {
		ListOfSuicides l = new ListOfSuicides("INPUT.txt");
		l.kill();
	}


}

class ListOfSuicides {
	private Integer amountOfSuicides;
	private Integer range;
	private CircleList mainList;

	public ListOfSuicides(String path)
	{
		try(Scanner input = new Scanner(Paths.get(path))) {
			if(input.hasNext()) {
				String parametersListOfSuicides = input.nextLine().strip();
				if(isCorrectParametersOfListOfSuicides(parametersListOfSuicides)) {
					String[] tokensParametersOfLabyrinth = parametersListOfSuicides.split(" ");
					int numberOfParameter = 0;
					amountOfSuicides = Integer.valueOf(tokensParametersOfLabyrinth[numberOfParameter]);
					numberOfParameter++;
					range = Integer.valueOf(tokensParametersOfLabyrinth[numberOfParameter]);
					if(this.amountOfSuicides == 1) {
						this.range = 0;
					}
				}
			}
			else {

			}
		}catch (IOException e) {

		}

		mainList = new CircleList();
		for(int i = 1; i < this.amountOfSuicides; i++) {
			new CircleList(mainList, i);
		}
	}
	public boolean isCorrectParametersOfListOfSuicides(String S)
	{
		return true;
	}

	public void kill()
	{
		for(;;) {
			if(1 == CircleList.sizeOfList) {
				this.mainList = CircleList.findElement(this.mainList, this.range);
				this.mainList.setNext(null);
				this.mainList.setPrevious(null);
				break;
			}
			if (CircleList.sizeOfList == this.amountOfSuicides) {
				this.mainList = CircleList.findElement(this.mainList, this.range - 1);
				CircleList.deleteElement(mainList);
			}
			else{
				this.mainList = CircleList.findElement(this.mainList, this.range);
				CircleList.deleteElement(mainList);
			}
		}
	}
}

class CircleList {
	private CircleList next;
	private CircleList previous;
	private Integer numberOfSuicide;
	static int sizeOfList = 0;

	public CircleList(CircleList currentCircleList, Integer number) {
		this.numberOfSuicide = number + 1;
		if(currentCircleList.getNext() != null) {
			currentCircleList.getPrevious().setNext(this);
			this.next = currentCircleList;
			this.previous = currentCircleList.getPrevious();
			currentCircleList.setPrevious(this);
		}
		else {
			currentCircleList.setNext(this);
			currentCircleList.setPrevious(this);
			this.next = currentCircleList;
			this.previous = currentCircleList;
		}
		CircleList.sizeOfList++;
	}

	public CircleList()
	{
		this.next = null;
		this.previous = null;
		this.numberOfSuicide = 1;
		CircleList.sizeOfList++;
	}

	public CircleList getNext() {
		return next;
	}

	public CircleList getPrevious() {
		return previous;
	}

	public Integer getNumberOfSuicide() {
		return numberOfSuicide;
	}

	public void setNext(CircleList next) {
		this.next = next;
	}

	public void setPrevious(CircleList previous) {
		this.previous = previous;
	}


	public static CircleList findElement(CircleList list, Integer number)
	{
		for(int i = 0; i < number; i++) {
			list = list.getNext();
		}
		return list;
	}

	static void deleteElement(CircleList list)
	{
		list.getPrevious().setNext(list.getNext());
		list.getNext().setPrevious(list.getPrevious());
		CircleList.sizeOfList--;
	}
}


