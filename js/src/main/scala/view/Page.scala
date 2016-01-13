package view

import com.thoughtworks.binding.JsPromiseBinding
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{ Binding, dom }
import org.scalajs.dom.raw.Promise

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport


/**
 * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
 */
class Page(stargazerCounterService: js.Dynamic) {

  @dom
  def render = {
    val userNameInput = UserNameInput()
    val userStargzerData = new JsPromiseBinding(stargazerCounterService.getStargazer(userNameInput.userName.each).asInstanceOf[Promise[js.Dynamic]])
    val stargazerStatus = new StargazerStatus(userStargzerData)
    <section>
      { userNameInput.render.each }{ stargazerStatus.render.each }
    </section>
  }

}

@JSExport
object Page {

  @JSExport
  def main(stargazerCounterService: js.Dynamic): Unit = {
    val page = new Page(stargazerCounterService)
    dom.render(org.scalajs.dom.document.body, page.render)
  }

}
