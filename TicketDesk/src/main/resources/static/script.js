// ======================================================
// TICKETDESK - COMPLETE FRONTEND JAVASCRIPT
// ======================================================

const API_BASE_URL = "/api";


// ======================================================
// PAGE LOAD
// ======================================================

document.addEventListener("DOMContentLoaded", function () {

    // Shows page
    if (document.getElementById("showGrid")) {
        loadShows();
    }

    // Bookings page
    if (document.getElementById("bookingGrid")) {
        loadBookings();
    }

});


// ======================================================
// SHOWS
// ======================================================


// ------------------------------------------------------
// LOAD ALL SHOWS
// ------------------------------------------------------

async function loadShows() {

    const showGrid =
        document.getElementById("showGrid");

    const message =
        document.getElementById("showMessage");

    if (!showGrid) {
        return;
    }

    try {

        if (message) {
            message.textContent =
                "Loading shows...";
        }

        const response =
            await fetch(`${API_BASE_URL}/shows`);

        if (!response.ok) {
            throw new Error(
                "Unable to fetch shows"
            );
        }

        const shows =
            await response.json();

        displayShows(shows);

    }

    catch (error) {

        console.error(
            "Error loading shows:",
            error
        );

        if (message) {
            message.textContent =
                "Unable to load shows.";
        }

        showGrid.innerHTML = `
            <div class="no-shows">

                <h3>
                    Unable to Load Shows
                </h3>

                <p>
                    Please make sure the backend
                    application is running.
                </p>

            </div>
        `;
    }
}



// ------------------------------------------------------
// FILTER SHOWS BY DATE
// ------------------------------------------------------

async function filterShows() {

    const dateInput =
        document.getElementById("showDate");

    const showGrid =
        document.getElementById("showGrid");

    const message =
        document.getElementById("showMessage");

    if (!dateInput || !showGrid) {
        return;
    }

    const date =
        dateInput.value;


    if (!date) {

        if (message) {
            message.textContent =
                "Please select a date.";
        }

        return;
    }


    try {

        if (message) {
            message.textContent =
                "Searching shows...";
        }

        showGrid.innerHTML = "";


        const response =
            await fetch(
                `${API_BASE_URL}/shows/search?date=${date}`
            );


        if (!response.ok) {

            throw new Error(
                "Unable to search shows"
            );
        }


        const shows =
            await response.json();


        displayShows(shows);

    }

    catch (error) {

        console.error(
            "Error filtering shows:",
            error
        );


        if (message) {

            message.textContent =
                "Unable to search shows.";

        }


        showGrid.innerHTML = `
            <div class="no-shows">

                <h3>
                    Search Failed
                </h3>

                <p>
                    Please try again.
                </p>

            </div>
        `;
    }
}



// ------------------------------------------------------
// DISPLAY SHOWS
// ------------------------------------------------------

function displayShows(shows) {

    const showGrid =
        document.getElementById("showGrid");

    const message =
        document.getElementById("showMessage");


    if (!showGrid) {
        return;
    }


    showGrid.innerHTML = "";


    if (!shows || shows.length === 0) {

        if (message) {

            message.textContent =
                "No shows found.";

        }


        showGrid.innerHTML = `
            <div class="no-shows">

                <h3>
                    No Shows Available
                </h3>

                <p>
                    Try selecting another date.
                </p>

            </div>
        `;

        return;
    }


    if (message) {

        message.textContent =
            `${shows.length} show${shows.length > 1 ? "s" : ""} available`;

    }


    shows.forEach(
        (show, index) => {

            createShowCard(
                show,
                index
            );

        }
    );
}



// ------------------------------------------------------
// CREATE SHOW CARD
// ------------------------------------------------------

function createShowCard(show, index) {

    const showGrid =
        document.getElementById("showGrid");


    if (!showGrid) {
        return;
    }


    const card =
        document.createElement("div");


    card.className =
        "show-card";


    card.innerHTML = `

        <div class="show-card-top">

            <div class="show-number">
                ${index + 1}
            </div>

            <div class="show-status">
                AVAILABLE
            </div>

        </div>


        <h2>
            ${escapeHTML(show.title)}
        </h2>


        <div class="show-info">

            <div class="show-info-item">

                <span>🕐</span>

                <span>
                    <strong>Time:</strong>
                    ${formatDateTime(show.time)}
                </span>

            </div>


            <div class="show-info-item">

                <span>🎟️</span>

                <span>
                    <strong>Total Seats:</strong>
                    ${show.totalSeats}
                </span>

            </div>

        </div>


        <div class="seat-info">

            <div class="seat-info-row">

                <span>
                    Remaining Seats
                </span>

                <strong id="seats-${show.id}">
                    Loading...
                </strong>

            </div>

        </div>


        <button
            class="book-button"
            id="book-${show.id}"
            onclick="bookShow(${show.id})">

            Book Now →

        </button>

    `;


    showGrid.appendChild(card);


    // Load remaining seats
    loadRemainingSeats(show.id);

}



// ------------------------------------------------------
// LOAD REMAINING SEATS
// ------------------------------------------------------

async function loadRemainingSeats(showId) {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/shows/${showId}/seats`
            );


        if (!response.ok) {

            throw new Error(
                "Unable to get remaining seats"
            );

        }


        const remainingSeats =
            await response.json();


        const seatElement =
            document.getElementById(
                `seats-${showId}`
            );


        const bookButton =
            document.getElementById(
                `book-${showId}`
            );


        if (seatElement) {

            seatElement.textContent =
                remainingSeats;

        }


        if (
            bookButton &&
            remainingSeats <= 0
        ) {

            bookButton.disabled =
                true;

            bookButton.textContent =
                "Sold Out";

        }

    }

    catch (error) {

        console.error(
            "Error loading seats:",
            error
        );


        const seatElement =
            document.getElementById(
                `seats-${showId}`
            );


        if (seatElement) {

            seatElement.textContent =
                "Unavailable";

        }

    }
}



// ------------------------------------------------------
// BOOK SHOW
// ------------------------------------------------------

function bookShow(showId) {

    window.location.href =
        `booking-form.html?showId=${showId}`;

}



// ======================================================
// BOOKINGS
// ======================================================


// ------------------------------------------------------
// LOAD ALL BOOKINGS
// ------------------------------------------------------

async function loadBookings() {

    const bookingGrid =
        document.getElementById("bookingGrid");

    const bookingMessage =
        document.getElementById("bookingMessage");


    if (!bookingGrid) {
        return;
    }


    try {

        if (bookingMessage) {

            bookingMessage.textContent =
                "Loading bookings...";

        }


        const response =
            await fetch(
                `${API_BASE_URL}/bookings`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load bookings"
            );

        }


        const bookings =
            await response.json();


        bookingGrid.innerHTML = "";


        // No bookings

        if (
            !bookings ||
            bookings.length === 0
        ) {

            if (bookingMessage) {

                bookingMessage.textContent =
                    "No bookings found.";

            }


            bookingGrid.innerHTML = `

                <div class="no-bookings">

                    <h2>
                        No bookings yet
                    </h2>

                    <p>
                        You haven't booked any shows yet.
                    </p>

                    <a href="shows.html">
                        Explore Shows
                    </a>

                </div>

            `;

            return;
        }


        // Booking count

        if (bookingMessage) {

            bookingMessage.textContent =
                `${bookings.length} booking${
                    bookings.length > 1
                        ? "s"
                        : ""
                } found`;

        }


        // Create cards

        bookings.forEach(
            (booking) => {

                createBookingCard(
                    booking
                );

            }
        );

    }

    catch (error) {

        console.error(
            "Error loading bookings:",
            error
        );


        if (bookingMessage) {

            bookingMessage.textContent =
                "Unable to load bookings.";

        }


        bookingGrid.innerHTML = `

            <div class="no-bookings">

                <h2>
                    Something went wrong
                </h2>

                <p>
                    Could not connect to the booking service.
                </p>

            </div>

        `;

    }
}



// ------------------------------------------------------
// CREATE BOOKING CARD
// ------------------------------------------------------

function createBookingCard(booking) {

    const bookingGrid =
        document.getElementById(
            "bookingGrid"
        );


    if (!bookingGrid) {
        return;
    }


    const status =
        booking.status || "CONFIRMED";


    const cancelled =
        status.toUpperCase() ===
        "CANCELLED";


    const card =
        document.createElement("div");


    card.className =
        "booking-card" +
        (
            cancelled
                ? " cancelled"
                : ""
        );


    card.innerHTML = `

        <div class="booking-card-header">

            <div class="booking-id">

                #${booking.id}

            </div>


            <div class="booking-status">

                ${escapeHTML(status)}

            </div>

        </div>


        <h2>
            Booking #${booking.id}
        </h2>


        <div class="booking-details">


            <div class="booking-detail">

                <span>
                    Student ID
                </span>

                <strong>
                    ${booking.studentId}
                </strong>

            </div>


            <div class="booking-detail">

                <span>
                    Show ID
                </span>

                <strong>
                    ${booking.showId}
                </strong>

            </div>


            <div class="booking-detail">

                <span>
                    Seat Number
                </span>

                <strong>
                    ${escapeHTML(
        booking.seatNumber
    )}
                </strong>

            </div>


            <div class="booking-detail">

                <span>
                    Status
                </span>

                <strong>
                    ${escapeHTML(status)}
                </strong>

            </div>


        </div>


        ${
        cancelled
            ? `
                    <button
                        class="cancel-button"
                        disabled>

                        Booking Cancelled

                    </button>
                `
            : `
                    <button
                        class="cancel-button"
                        onclick="cancelBooking(${booking.id})">

                        Cancel Booking

                    </button>
                `
    }

    `;


    bookingGrid.appendChild(card);

}



// ------------------------------------------------------
// CANCEL BOOKING
// ------------------------------------------------------

async function cancelBooking(id) {

    const confirmCancel =
        confirm(
            "Are you sure you want to cancel this booking?"
        );


    if (!confirmCancel) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/bookings/${id}/cancel`,
                {
                    method: "PUT"
                }
            );


        if (!response.ok) {

            const errorText =
                await response.text();


            throw new Error(
                errorText ||
                "Failed to cancel booking"
            );

        }


        alert(
            "Booking cancelled successfully!"
        );


        // Refresh booking list

        await loadBookings();

    }

    catch (error) {

        console.error(
            "Error cancelling booking:",
            error
        );


        alert(
            "Unable to cancel the booking."
        );

    }

}



// ======================================================
// UTILITY FUNCTIONS
// ======================================================


// ------------------------------------------------------
// FORMAT DATE AND TIME
// ------------------------------------------------------

function formatDateTime(dateTime) {

    if (!dateTime) {

        return "Not specified";

    }


    const date =
        new Date(dateTime);


    if (
        isNaN(
            date.getTime()
        )
    ) {

        return dateTime;

    }


    return date.toLocaleString(
        "en-IN",
        {
            dateStyle: "medium",
            timeStyle: "short"
        }
    );

}



// ------------------------------------------------------
// ESCAPE HTML
// ------------------------------------------------------

function escapeHTML(value) {

    if (
        value === null ||
        value === undefined
    ) {

        return "";

    }


    return String(value)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );

}