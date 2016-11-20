package br.unb.cic.redes.Model

import br.unb.cic.redes.Model.models.User
import br.unb.cic.redes.Model.models.Message
import br.unb.cic.redes.Model.models.Group

import scala.collection.mutable.{ListBuffer, MutableList}

/**
  * Created by @brenoxp on 19/10/16.
  */
object Manager {

  private val MOTD = "Welcome to Scala Chat \nA nice place to meet people!"

  private val users = ListBuffer[User]()
  private val groups = ListBuffer[Group]()


  /* Methods */
  def addUser(user: User) = users += user
  def removeUser(user: User) = users -= user

  private def addGroup(group: Group) = groups += group
  private def removeGroup(group: Group) = groups -= group

  def receiveMessage(msg: Message): Unit = {
    val m = msg.message.split(" ")

    if (m.isEmpty) return ()

    m(0) match {
      case "/help" => if (m.length == 1) help(msg) else unrecognizedCommand(msg)
      case "/nick" => if (m.length <= 2) nick(msg) else unrecognizedCommand(msg)
      case "/leave" => unrecognizedCommand(msg)
      case "/join" => if (m.length == 2) join(msg) else unrecognizedCommand(msg)
      case "/create" => if (m.length == 2) create(msg) else unrecognizedCommand(msg)
      case "/delete" => if (m.length == 2) delete(msg) else unrecognizedCommand(msg)
      case "/list" => if (m.length == 1) list(msg) else unrecognizedCommand(msg)
      case "/away" => unrecognizedCommand(msg)
      case "/msg nick mensagem" => unrecognizedCommand(msg)
      case "/ban nick" => unrecognizedCommand(msg)
      case "/kick nick" => unrecognizedCommand(msg)
      case "/clear" => unrecognizedCommand(msg)
      case _: String => unrecognizedCommand(msg)
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
     "Seja muito bem-vindo a este Servidor"
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
      sendMessage(message, "Nickname: " + message.user.nickname)
    }

    def changeNick(message: Message) = {
      val newNick = message.message.split(" ")(1)

      if (newNick.length > 50) sendMessage(message, "Nickname deve ter até 50 caracteres")
      else if (users.exists(user => user.nickname == newNick)) sendMessage(message, "Nickname em uso")
      else {
        message.user.nickname = newNick
        sendMessage(message, "Nickname alterado")
      }
    }
  }

  private def create(message: Message) = {
    val split = message.message.split(" ")

    val groupName = split(1)
    if (groupName.length > 50) sendMessage(message, "Nome do grupo deve ter até 50 caracteres")
    else if (groups.exists(group => group.groupName == groupName)) sendMessage(message, "Nome do grupo em uso")
    else {
      val group = Group(message.user, groupName)
      addGroup(group)
      message.user.addGroup(group)
      message.user.currentGroup = group
      sendMessage(message, groupName + " criado com sucesso.\nVocê está no grupo: " + groupName)
    }
  }

  def delete(message: Message) = {
    val groupName = message.message.split(" ")(1)
    val groupToRemove = groups.filter(group => group.groupName == groupName).head

    if (groupToRemove == null) sendMessage(message, "Grupo não existe")
    else if (!groupToRemove.canBeRemoved) {
      sendMessage(message, "Grupo não pode ser removido")
    } else {
      removeGroup(groupToRemove)
      message.user.removeGroup(groupToRemove)
      sendMessage(message, "Grupo removido com sucesso")
    }
  }

  def list(message: Message) = {

    val currentGroup = message.user.currentGroup

    if (currentGroup == null) {
      var groupNames = "Grupos Cadastrados:\n"
      groups.foreach(group => groupNames += group.groupName + "\n")
      sendMessage(message, groupNames.substring(0, groupNames.length - 1))
    } else {
      var userNames = "Usuários Cadastrados:\n"
      currentGroup.getUsers.foreach(user => userNames += user.nickname + "\n")
      sendMessage(message, userNames.substring(0, userNames.length - 1))
    }
  }

  def join(message: Message) = {
    if (message.user.currentGroup != null) {
      sendMessage(message, "Para entrar em outro grupo primeiro deve sair deste")
    } else {
      val split = message.message.split(" ")

      val groupName = split(1)
      if (groups.exists(group => group.groupName == groupName)) {
        message.user.currentGroup = groups.filter(group => group.groupName == groupName)(0)
        message.user.currentGroup.addPerson(message.user)
        sendMessage(message, "Você está no grupo: " + groupName)
      } else {
        sendMessage(message, "Não existe grupo com este nome")
      }
    }
  }

}
