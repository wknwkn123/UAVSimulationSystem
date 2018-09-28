/**
 * WS is a custom websocket which is built on top of WebSocket
 * @param {String} url
 * @param {function} onWSConnected
 * @param {function} onWSDisconnected
 * @param {function} onError
 * @param {function} onMessage
 */
class WS {
  constructor (props) {
    this.props = props
  }

  setupWebsocket = () => {
    const { onWSConnected, onWSDisconnected, onMessage, onError } = this.props
    this.ws.onopen = () => {
      onWSConnected && onWSConnected()
    }

    this.ws.onclose = () => {
      onWSDisconnected && onWSDisconnected()
      const waitingTime = 1000 // in ms
      setTimeout(this.connectWebsocket, waitingTime)
    }

    this.ws.onerror = error => {
      onError && onError(error)
    }

    this.ws.onmessage = event => {
      onMessage && onMessage(event)
      //console.log(event)
    }
  }

  connectWebsocket = () => {
    const { url } = this.props
    this.ws = new WebSocket(url)
    this.setupWebsocket()
  }

  start = () => {
    this.connectWebsocket()
  }

  stop = () => {
    this.ws = null
  }

  send = data => {
    this.ws.send(JSON.stringify(data))
  }
}

export default WS
