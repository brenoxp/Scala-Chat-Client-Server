package br.unb.cic.redes.Model

import br.unb.cic.redes.Model.models.User
import br.unb.cic.redes.Model.models.Message

import scala.collection.mutable

/**
  * Created by @brenoxp on 19/10/16.
  */
object Manager {

  private val MOTD = "Welcome to Scala Chat \nA nice place to meet people!"

  private val users = mutable.MutableList[User]()


  /* Methods */

  def addUser(user: User) = users += user
  def removeUser(user: User) = ???

  def receiveMessage(message: Message): Unit = {
    val m = message.message.split(" ")

    if (m.isEmpty) return ()

    m(0) match {
      case "/help" => help(message)
      case "/nick" => if (m.length <= 2) nick(message) else unrecognizedCommand(message)
      case "/leave" => ()
      case "/join grupo" => ()
      case "/create grupo" => ()
      case "/delete grupo" => ()
      case "/away" => ()
      case "/msg nick mensagem" => ()
      case "/ban nick" => ()
      case "/kick nick" => ()
      case "/clear" => ()
      case _: String => unrecognizedCommand(message)
    }

  }

  def sendMessage(message: Message, text: String) = message.user.sendMessage(Message(text))
  def unrecognizedCommand(message: Message) =
    message.user.sendMessage(Message("Unrecognized command: \"" + message.message + "\""))



  private def help(message: Message) = {
    sendMessage(message, helpAux)
  }

  def welcomeHelp(message: Message) =  {
   val helpMessage = helpAux + "\n\n" +
     "Seja muito bem-vindo a este Servidor\n"
    sendMessage(message, helpMessage)
  }

  private val helpAux = {
    "Chat Redes de Computadores = versão 1.0\n" +
      MOTD + "\n" +
      "---------- Comandos disponíveis ----------\n" +
      "/help ---------------------- Exibe esta tela de dajuda\n" +
      "/nick nome ----------------- Altera o nickname do usuário pelo parâmetro nome\n" +
      "/leave --------------------- Sai do ambiente atual para o anterior. Quando dentro de um Chat volta a tela inicial, na tela inicial sai do programa\n" +
      "/list ---------------------- Lista os grupos disponíveis no servidor. Dentro do Chat lista todos os usuários na conversa\n" +
      "/join grupo ---------------- Acessa o grupo especificado\n" +
      "/create grupo -------------- Cria um novo grupo\n" +
      "/delete grupo -------------- Exclui um grupo existente\n" +
      "/away ---------------------- Informa que o usuário está temporariamente indisponível\n" +
      "/msg nick messagem --------- Envia uma mensagem privada a um usuário descrito por nick\n" +
      "/ban nick ------------------ Expulsa o usuário temporariamente do grupo\n" +
      "/clear --------------------- Limpa todas as mensagens da tela\n" +
      "/file caminho_para_arquivo - Envia arquivo ao grupo\n" +
      "/list_files ---------------- Lista todos os arquivos disponíveis no grupo\n" +
      "/get_file nome_arquivo ----- Obtém o arquivo disponível\n" +
      "------------------------------------------"
  }


  private def nick(message: Message) = {
    val split = message.message.split(" ")

    if (split.length <= 1) printNick(message)
    else changeNick(message)

    def printNick(message: Message) = {
      sendMessage(message, message.user.nickname)
    }

    def changeNick(message: Message) = {
      val newNick = message.message.split(" ")(1)
      if (users.exists(user => user.nickname == newNick)) {
        sendMessage(message, "Nickname em uso")
      } else {
        message.user.nickname = newNick
        sendMessage(message, "Nickname alterado")
      }
    }
  }

  def createGroup = {
  }




}
