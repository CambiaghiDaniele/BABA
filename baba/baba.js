const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

let player = {
    xMovement: null,
    yMovement: null,
    color: 'blue',
};

const pointSize = 1;

// Imposta la dimensione iniziale della canvas alla dimensione della finestra
resizeCanvas();

// Aggiungi un gestore di eventi per la ridimensionamento della finestra
window.addEventListener('resize', resizeCanvas);

function resizeCanvas() {
    // Imposta la dimensione della canvas uguale alla dimensione della finestra
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    // Aggiorna il gioco quando la canvas viene ridimensionata
    drawGame();
}

let lastMessageTime = 0;
const messageInterval = 100; // Intervallo di tempo in millisecondi

const socket = new WebSocket('ws://localhost:12345/baba');

socket.onopen = function (event) {
    console.log('WebSocket connection opened:', event);
};

socket.onmessage = function (event) {
    console.log('WebSocket message received:', event.data);
    const data = JSON.parse(event.data);
};

function drawPlayer() {
    ctx.beginPath();
    ctx.arc(canvas.width / 2, canvas.height / 2, data.player.size, 0, 2 * Math.PI);
    ctx.fillStyle = player.color;
    ctx.fill();
    ctx.closePath();
}

function drawEnemies() {
    data.enemies.forEach(enemy => {
        ctx.beginPath();
        ctx.arc(enemy.x, enemy.y, enemy.size, 0, 2 * Math.PI);
        ctx.fillStyle = enemy.color;
        ctx.fill();
        ctx.closePath();
    });
}

function drawPoints() {
    data.points.forEach(point => {
        ctx.beginPath();
        ctx.arc(point.x, point.y, pointSize, 0, 2 * Math.PI);
        ctx.fillStyle = point.color;
        ctx.fill();
        ctx.closePath();
    });
}

function updateGame() {
    const now = Date.now();

    // Verifica se è passato l'intervallo di tempo e se il movimento è diverso da undefined
    if ((now - lastMessageTime) > messageInterval && (player.xMovement !== null || player.yMovement !== null)) {
        const message = JSON.stringify({
            type: 'playerUpdate',
            xMovement: player.xMovement,
            yMovement: player.yMovement
        });
        socket.send(message);
        lastMessageTime = now; // Aggiorna l'orario dell'ultimo messaggio inviato
    }
}

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Disegna gli elementi del gioco qui
    drawPlayer();
    drawEnemies();
    drawPoints();
}

// Aggiungi eventi di ascolto per i tasti
document.addEventListener('keydown', function (event) {
    handleKeyDown(event);
});

document.addEventListener('keyup', function (event) {
    handleKeyUp(event);
});

function handleKeyDown(event) {
    // Gestisci i tasti premuti
    switch (event.key) {
        case 'ArrowUp':
        case 'w':
            player.yMovement = true;
            break;
        case 'ArrowDown':
        case 's':
            player.yMovement = false;
            break;
        case 'ArrowLeft':
        case 'a':
            player.xMovement = true;
            break;
        case 'ArrowRight':
        case 'd':
            player.xMovement = false;
            break;
    }
}

function handleKeyUp(event) {
    // Gestisci i tasti rilasciati
    switch (event.key) {
        case 'ArrowUp':
        case 'w':
            player.yMovement = undefined;
            break;
        case 'ArrowDown':
        case 's':
            player.yMovement = undefined;
            break;
        case 'ArrowLeft':
        case 'a':
            player.xMovement = undefined;
            break;
        case 'ArrowRight':
        case 'd':
            player.xMovement = undefined;
            break;
    }
}

function gameLoop() {
    updateGame();
    drawGame();
    requestAnimationFrame(gameLoop);
}

gameLoop();
