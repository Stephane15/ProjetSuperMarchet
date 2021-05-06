package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="SuperMarcher")
public class SuperMarcherListWrapper
	{
		private List<SuperMarcher> SuperMarcher;
		@XmlElement(name = "SuperMarcher")
		public List<SuperMarcher> getSuperMarchers()
		{
				return getSuperMarchers();
		}
		public void setSuperMarcher(List<SuperMarcher> SuperMArchers)
		{
				this.SuperMarcher=SuperMarcher;
		}
	}
