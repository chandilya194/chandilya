document.addEventListener('DOMContentLoaded', function() {
    loadRecognitions();
    
    document.getElementById('recognitionForm').addEventListener('submit', function(e) {
        e.preventDefault();
        addRecognition();
    });
});

function showAddForm() {
    document.getElementById('addForm').style.display = 'block';
}

function hideAddForm() {
    document.getElementById('addForm').style.display = 'none';
    document.getElementById('recognitionForm').reset();
}

function loadRecognitions() {
    fetch('/student-recognition-wall/api/recognitions')
        .then(response => response.json())
        .then(data => {
            displayRecognitions(data);
        })
        .catch(error => {
            console.error('Error loading recognitions:', error);
            displayRecognitions([]);
        });
}

function addRecognition() {
    const formData = {
        studentName: document.getElementById('studentName').value,
        achievement: document.getElementById('achievement').value,
        description: document.getElementById('description').value
    };

    fetch('/student-recognition-wall/api/recognitions', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        hideAddForm();
        loadRecognitions();
    })
    .catch(error => {
        console.error('Error adding recognition:', error);
        alert('Error adding recognition. Please try again.');
    });
}

function displayRecognitions(recognitions) {
    const wall = document.getElementById('recognitionWall');
    
    if (recognitions.length === 0) {
        wall.innerHTML = '<div style="text-align: center; color: white; font-size: 1.2em; grid-column: 1/-1;">No recognitions yet. Be the first to add one!</div>';
        return;
    }
    
    wall.innerHTML = recognitions.map(recognition => `
        <div class="recognition-card">
            <div class="student-name">${recognition.studentName}</div>
            <div class="achievement">${recognition.achievement}</div>
            <div class="description">${recognition.description || 'Great achievement!'}</div>
            <div class="date">${new Date(recognition.date).toLocaleDateString()}</div>
        </div>
    `).join('');
}