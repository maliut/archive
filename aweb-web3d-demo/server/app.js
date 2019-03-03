var app = require('http').createServer(function (request, response) {
    response.writeHead(200, {'Content-Type': 'text/plain'});
    response.end('Hello World\n');
});
var io = require('socket.io')(app);
app.listen(5000);

io.on('connection', function(socket) {
    io.sockets.emit("web3d.msg", {
        action: 's',
        userId: socket.id
    });

    socket.on("web3d.pos", function(data) {
        io.sockets.emit("web3d.msg", {
            action: 'u',
            userId: socket.id,
            position: data
        });
    });

    socket.on("web3d.chat", function(data) {
        io.sockets.emit("web3d.chat", {
            userId: socket.id,
            content: data
        });
    });

    socket.on("disconnect", function() {
        io.sockets.emit("web3d.msg", {
            action: 'd',
            userId: socket.id
        });
    });
});

