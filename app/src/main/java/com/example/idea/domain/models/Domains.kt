package com.example.idea.domain.models


data class Domains(
    val domainsWithSubdomains: Map<String, List<String>> = mapOf(
        "Technology" to listOf(
            "Artificial Intelligence (AI)",
            "Machine Learning (ML)",
            "Internet of Things (IoT)",
            "Mobile App Development",
            "Web Development",
            "Cyber-security",
            "Data Science",
            "Augmented Reality (AR) and Virtual Reality (VR)",
            "Blockchain"
        ),
        "Sustainability and Environmental" to listOf(
            "Renewable Energy",
            "Waste Management",
            "Sustainable Agriculture",
            "Water Conservation",
            "Green Building and Architecture",
            "Climate Change Mitigation",
            "Conservation and Biodiversity"
        ),
        "Health and Wellness" to listOf(
            "Fitness and Exercise",
            "Nutrition and Diet",
            "Mental Health",
            "Healthcare Management",
            "Disease Prevention and Monitoring",
            "Medical Technology",
            "Personalized Medicine"
        ),
        "Education" to listOf(
            "E-learning and Online Courses",
            "Educational Games and Apps",
            "Language Learning",
            "STEM Education",
            "Skills Training",
            "Virtual Reality in Education"
        ),
        "Social Impact" to listOf(
            "Poverty Alleviation",
            "Accessible Technology",
            "Human Rights and Advocacy",
            "Civic Engagement",
            "Community Development",
            "Disaster Management",
            "Refugee Support"
        ),
        "Arts and Entertainment" to listOf(
            "Music and Audio",
            "Film and Video Production",
            "Gaming and Game Development",
            "Visual Arts and Design",
            "Virtual Reality (VR) Art",
            "Creative Writing and Storytelling",
            "Performing Arts"
        ),
        "Business and Entrepreneurship" to listOf(
            "E-commerce",
            "Marketing and Advertising",
            "Financial Technology (Fintech)",
            "Supply Chain Management",
            "Customer Relationship Management (CRM)",
            "Project Management",
            "Business Analytics"
        ),
        "Science and Research" to listOf(
            "Space Exploration",
            "Astrophysics and Astronomy",
            "Biotechnology",
            "Nanotechnology",
            "Quantum Computing",
            "Robotics",
            "Materials Science"
        ),
        "Social Media and Communication" to listOf(
            "Social Networking Platforms",
            "Content Creation and Sharing",
            "Online Communities",
            "Messaging and Video Calling Apps",
            "Influenced Marketing",
            "Digital Marketing Strategies",
            "Privacy and Security in Social Media"
        ),
        "Personal Development" to listOf(
            "Time Management and Productivity",
            "Goal Setting and Achievement",
            "Mindfulness and Meditation",
            "Self-improvement Apps",
            "Life Coaching",
            "Personal Finance Management",
            "Healthy Habits Tracking"
        )
    )
)














