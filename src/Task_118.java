import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Task_118 {
	public static void main(String[] args) {
		ListOfSuicides test = new ListOfSuicides();
		try(Formatter output = new Formatter("OUTPUT.TXT")) {
			output.format(test.toString());
		}catch (FileNotFoundException | FormatterClosedException e) {
			e.printStackTrace();
		}
	}

	//-----------------------------------------------------------------------------
	/*public*/static class ListOfSuicides {
		//-----------------------------------------------------------------------------fields
		private Integer amountOfSuicides;
		private Integer range;//through how much people we kill next man
		private CircleList mainList;//pointer to one Suicides
		//-----------------------------------------------------------------------------constructors
		/*public*/ private ListOfSuicides(String path)
		{
			this.amountOfSuicides = 0;
			this.range = 0;
			try(Scanner input = new Scanner(Paths.get(path))) {
				//-----------------------------------------------------------------------------
				if(input.hasNext()) {
					//-----------------------------------------------------------------------------
					String parametersListOfSuicides = input.nextLine();//read data from file
					//-----------------------------------------------------------------------------
					if(this.isCorrectParametersOfListOfSuicides(parametersListOfSuicides)) {
						String[] tokensParametersOfSuicides = parametersListOfSuicides.split(" ");
						int numberOfParameter = 0;
						this.amountOfSuicides = Integer.valueOf(tokensParametersOfSuicides[numberOfParameter++]);
						this.range = Integer.valueOf(tokensParametersOfSuicides[numberOfParameter]);
						if(this.amountOfSuicides == 1) {//if we have only one Suicide he should live
							this.range = 0;
						}
					}
					//-----------------------------------------------------------------------------
					else{
						throw new IOException("Incorrect value in file!");
					}
					//-----------------------------------------------------------------------------
				}
				//-----------------------------------------------------------------------------
				else {
					throw new IOException("File is empty!");
				}
				//-----------------------------------------------------------------------------
			}catch (IOException | NoSuchElementException e) {
				e.printStackTrace();
			}
			//-----------------------------------------------------------------------------
			this.mainList = new CircleList();//create first suicide
			for(int suicide = 1; suicide < this.amountOfSuicides; suicide++) {
				new CircleList(this.mainList, suicide);//create the rest of suicides
			}
		}

		/*public*/ ListOfSuicides()
		{
			this("INPUT.TXT");
		}
		//-----------------------------------------------------------------------------methods for constructors
		private boolean isCorrectParametersOfListOfSuicides(String s)
		{
			if(s.matches("[1-9]\\d* [1-9]\\d*")){
				String[] tokens = s.split(" ");
				int check = 500;
				for(String tempS : tokens) {
					if((Integer.valueOf(tempS) <= check)) {
						check-= 400;
					}
					else{
						return false;
					}
				}
			}
			else{
				return false;
			}

			return true;
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------methods
		private int kill()
		{
			for(;;) {
				//-----------------------------------------------------------------------------
				if(1 == CircleList.sizeOfList) {//correct element of survive suicide
					this.mainList = CircleList.findElement(this.mainList, this.range);
					this.mainList.setNext(null);
					this.mainList.setPrevious(null);
					break;
				}
				//-----------------------------------------------------------------------------
				//-----------------------------------------------------------------------------
				if (CircleList.sizeOfList == this.amountOfSuicides) {//first iteration of suicides should be with range - 1
					//because first suicide already as if keep in mind
					this.mainList = CircleList.findElement(this.mainList, this.range - 1);
					CircleList.deleteElement(this.mainList);
				}
				else{
					this.mainList = CircleList.findElement(this.mainList, this.range);
					CircleList.deleteElement(this.mainList);
				}
				//-----------------------------------------------------------------------------
			}
			return this.mainList.getNumberOfSuicide();
		}

		@Override
		public String toString()
		{
			return String.valueOf(this.kill());
		}

	}

	/*public*/static class CircleList {
		//-----------------------------------------------------------------------------fields
		private CircleList next;
		private CircleList previous;
		private Integer numberOfSuicide;
		static int sizeOfList = 0;
		//-----------------------------------------------------------------------------constructors
		/*public*/ CircleList(CircleList currentCircleList, Integer number) {
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

		/*public*/ CircleList()
		{
			this.next = null;
			this.previous = null;
			this.numberOfSuicide = 1;
			CircleList.sizeOfList++;
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------methods
		/*public*/ private CircleList getNext() {
			return this.next;
		}

		/*public*/ private CircleList getPrevious() {
			return this.previous;
		}

		/*public*/ Integer getNumberOfSuicide() {
			return numberOfSuicide;
		}

		/*public*/ void setNext(CircleList next) {
			this.next = next;
		}

		/*public*/ void setPrevious(CircleList previous) {
			this.previous = previous;
		}

		static CircleList findElement(CircleList list, Integer number)
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
		//-----------------------------------------------------------------------------
	}
}