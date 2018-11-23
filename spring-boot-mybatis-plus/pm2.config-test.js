module.exports = {
    apps: [
        {
            name: 'asoco-dangerchemical-waste-server',
            script: 'java',
            args: [
                '-jar',
                'target/asoco-dangerchemical-waste-server-0.0.1.jar',
                '--spring.profiles.active=test'
                // '--spring.datasource.password=password',
            ],
            cwd: '.',
            interpreter: ''
        }
    ]
}
