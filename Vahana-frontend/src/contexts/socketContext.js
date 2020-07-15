import React from 'react';

const SocketContext = React.createContext();
const SocketProvider = SocketContext.Provider;
const SocketConsumer = SocketContext.Consumer; 

export default SocketContext;
export {SocketProvider, SocketConsumer};
