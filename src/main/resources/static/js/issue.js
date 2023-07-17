function setState() {
    const issueId = document.getElementById("issue-id").value;
    if (!issueId) {
        return;
    }

    const newState = document.getElementById("select-state").value;

    axios.patch(`/issues/state/${issueId}`, {state: newState}).then(response => {
        console.log(response);
    }).catch(error => {
        console.log(error);
    })
}

function setPriority() {
    const issueId = document.getElementById("issue-id").value;
    if (!issueId) {
        return;
    }

    const newPriority = document.getElementById("select-priority").value;

    axios.patch(`/issues/priority/${issueId}`, {priority: newPriority}).then(response => {
        console.log(response);
    }).catch(error => {
        console.log(error);
    })
}