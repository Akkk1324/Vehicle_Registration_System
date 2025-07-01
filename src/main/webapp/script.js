// DOM elements
const navBtns = document.querySelectorAll('.nav-btn');
const sections = document.querySelectorAll('.section');
const registrationForm = document.getElementById('registrationForm');
const searchBtn = document.getElementById('searchBtn');
const loadAllBtn = document.getElementById('loadAllBtn');
const searchResults = document.getElementById('searchResults');
const allRegistrations = document.getElementById('allRegistrations');
const notification = document.getElementById('notification');

// Navigation
navBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const targetSection = btn.getAttribute('data-section');
        switchSection(targetSection);
        
        // Update active nav button
        navBtns.forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
    });
});

function switchSection(targetSection) {
    sections.forEach(section => {
        section.classList.remove('active');
        if (section.id === targetSection) {
            section.classList.add('active');
        }
    });
}

// Notification system
function showNotification(message, type = 'success') {
    notification.textContent = message;
    notification.className = `notification ${type} show`;
    
    setTimeout(() => {
        notification.classList.remove('show');
    }, 5000);
}

// Registration form submission
if (registrationForm) {
    registrationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const submitBtn = e.target.querySelector('.submit-btn');
        submitBtn.disabled = true;
        submitBtn.textContent = 'Registering...';
        
        try {
            const formData = new FormData(registrationForm);
            
            console.log('Form data being sent:');
            for (let [key, value] of formData.entries()) {
                console.log(key, value);
            }
            
            const response = await fetch('/api/registrations/', {
                method: 'POST',
                body: formData
            });
            
            console.log('Response status:', response.status);
            const responseText = await response.text();
            console.log('Response text:', responseText);
            
            let result;
            try {
                result = JSON.parse(responseText);
            } catch (parseError) {
                throw new Error(`Server returned invalid JSON: ${responseText}`);
            }
            
            if (!response.ok) {
                throw new Error(result.error || `Server error: ${response.status}`);
            }
            
            showNotification('Vehicle registered successfully!', 'success');
            registrationForm.reset();
            
            // Show registration details
            showRegistrationDetails(result);

        } catch (error) {
            console.error('Full error object:', error);
            let errorMessage = 'Network error occurred';

            if (error.message) {
                errorMessage = error.message;
            } else if (typeof error === 'string') {
                errorMessage = error;
            }

            showNotification(errorMessage, 'error');
        } finally {
            // Re-enable submit button
            submitBtn.disabled = false;
            submitBtn.textContent = 'Register Vehicle';
        }
    });
}

// Search setup
if (searchBtn) {
    searchBtn.addEventListener('click', performSearch);
    
    // Allow search on Enter key
    document.getElementById('searchQuery').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            performSearch();
        }
    });
}

async function performSearch() {
    const searchType = document.getElementById('searchType').value;
    const searchQuery = document.getElementById('searchQuery').value.trim();
    
    if (!searchQuery) {
        showNotification('Please enter a search term', 'error');
        return;
    }
    
    searchResults.innerHTML = '<div class="loading">Searching...</div>';
    
    try {
        const response = await fetch(`/api/registrations/search?type=${searchType}&q=${encodeURIComponent(searchQuery)}`);
        const result = await response.json();
        
        if (!response.ok) {
            throw new Error(result.error || 'Search failed');
        }
        
        displaySearchResults(result, searchType);
        
    } catch (error) {
        console.error('Search error:', error);
        searchResults.innerHTML = `<div class="empty-state"><h3>Search Error</h3><p>${error.message}</p></div>`;
        showNotification('Search failed: ' + error.message, 'error');
    }
}

function displaySearchResults(result, searchType) {
    if (!result) {
        searchResults.innerHTML = '<div class="empty-state"><h3>No Results Found</h3><p>No registrations match your search criteria.</p></div>';
        return;
    }
    
    let registrations = [];
    if (searchType === 'email') {
        registrations = Array.isArray(result) ? result : [];
    } else {
        registrations = [result];
    }
    
    if (registrations.length === 0) {
        searchResults.innerHTML = '<div class="empty-state"><h3>No Results Found</h3><p>No registrations match your search criteria.</p></div>';
        return;
    }
    
    const html = registrations.map(registration => createRegistrationHTML(registration)).join('');
    searchResults.innerHTML = html;
}

// Load all registrations
if (loadAllBtn) {
    loadAllBtn.addEventListener('click', async () => {
        allRegistrations.innerHTML = '<div class="loading">Loading registrations...</div>';
        
        try {
            const response = await fetch('/api/registrations/');
            const registrations = await response.json();
            
            if (!response.ok) {
                throw new Error('Failed to load registrations');
            }
            
            if (registrations.length === 0) {
                allRegistrations.innerHTML = '<div class="empty-state"><h3>No Registrations</h3><p>No vehicle registrations found in the system.</p></div>';
                return;
            }
            
            const html = registrations.map(registration => createRegistrationHTML(registration)).join('');
            allRegistrations.innerHTML = html;
            
        } catch (error) {
            console.error('Load error:', error);
            allRegistrations.innerHTML = `<div class="empty-state"><h3>Loading Error</h3><p>${error.message}</p></div>`;
            showNotification('Failed to load registrations: ' + error.message, 'error');
        }
    });
}

// Create HTML for registration display
function createRegistrationHTML(registration) {
    return `
        <div class="registration-card">
            <div class="registration-header">
                <div class="registration-number">${registration.registrationNumber}</div>
                <div class="status ${registration.status}">${registration.status}</div>
            </div>
            <div class="registration-details">
                <div class="detail-section">
                    <h4>Customer Information</h4>
                    <div class="detail-item">
                        <span class="detail-label">Name:</span>
                        <span class="detail-value">${registration.customer.firstName} ${registration.customer.lastName}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value">${registration.customer.email}</span>
                    </div>
                    ${registration.customer.phone ? `
                    <div class="detail-item">
                        <span class="detail-label">Phone:</span>
                        <span class="detail-value">${registration.customer.phone}</span>
                    </div>` : ''}
                    ${registration.customer.address ? `
                    <div class="detail-item">
                        <span class="detail-label">Address:</span>
                        <span class="detail-value">${registration.customer.address}</span>
                    </div>` : ''}
                    ${registration.customer.city ? `
                    <div class="detail-item">
                        <span class="detail-label">City:</span>
                        <span class="detail-value">${registration.customer.city}, ${registration.customer.state} ${registration.customer.zipCode}</span>
                    </div>` : ''}
                </div>
                
                <div class="detail-section">
                    <h4>Vehicle Information</h4>
                    <div class="detail-item">
                        <span class="detail-label">VIN:</span>
                        <span class="detail-value">${registration.vehicle.vin}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Make/Model:</span>
                        <span class="detail-value">${registration.vehicle.make} ${registration.vehicle.model}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Year:</span>
                        <span class="detail-value">${registration.vehicle.year}</span>
                    </div>
                    ${registration.vehicle.color ? `
                    <div class="detail-item">
                        <span class="detail-label">Color:</span>
                        <span class="detail-value">${registration.vehicle.color}</span>
                    </div>` : ''}
                    ${registration.vehicle.vehicleType ? `
                    <div class="detail-item">
                        <span class="detail-label">Type:</span>
                        <span class="detail-value">${registration.vehicle.vehicleType}</span>
                    </div>` : ''}
                </div>
                
                <div class="detail-section">
                    <h4>Registration Details</h4>
                    <div class="detail-item">
                        <span class="detail-label">License Plate:</span>
                        <span class="detail-value">${registration.licensePlate}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Registration Date:</span>
                        <span class="detail-value">${registration.registrationDate}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Expiration Date:</span>
                        <span class="detail-value">${registration.expirationDate}</span>
                    </div>
                    ${registration.registrationFee ? `
                    <div class="detail-item">
                        <span class="detail-label">Fee:</span>
                        <span class="detail-value">$${registration.registrationFee}</span>
                    </div>` : ''}
                </div>
            </div>
        </div>
    `;
}

function showRegistrationDetails(registration) {
    const detailsHtml = createRegistrationHTML(registration);

    // Switch to search section and show the new registration
    switchSection('search');
    searchResults.innerHTML = detailsHtml;

    // Clear search inputs
    document.getElementById('searchQuery').value = '';
}

// Auto-generate VIN validation
document.getElementById('vin').addEventListener('input', function() {
    const vin = this.value.toUpperCase();
    this.value = vin;

    if (vin.length === 17) {
        // Basic VIN validation (simplified)
        const validChars = /^[A-HJ-NPR-Z0-9]{17}$/;
        if (!validChars.test(vin)) {
            this.setCustomValidity('Invalid VIN format');
        } else {
            this.setCustomValidity('');
        }
    } else {
        this.setCustomValidity('VIN must be exactly 17 characters');
    }
});

// Auto-format license plate
document.getElementById('licensePlate').addEventListener('input', function() {
    this.value = this.value.toUpperCase();
});
