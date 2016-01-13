package view

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{ Binding, dom }
import org.scalajs.dom.raw.Promise
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.dom.raw.Event


/**
 * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
 */
final case class UserNameInput(userName: Var[String] = Var("")) {

  @dom
  def render = {
    <div>
      User Name:
      <input type="text" onchange={ event: Event => userName := event.target.asInstanceOf[HTMLInputElement].value } value={ userName.each }/>
    </div>
  }

}
