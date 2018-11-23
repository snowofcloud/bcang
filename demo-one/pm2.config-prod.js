module.exports = {
    apps: [
        {
            name: 'asoco-dangerchemical-waste-server-prod',
            script: 'java',
            args: [
                '-jar',
                'target/asoco-dangerchemical-waste-server-0.0.1.jar',
                '--spring.profiles.active=prod'
                // '--spring.datasource.password=password',
            ],
            cwd: '.',
            interpreter: ''
        }
    ]
}
