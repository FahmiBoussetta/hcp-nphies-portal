import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './encounter.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEncounterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EncounterDetail = (props: IEncounterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { encounterEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="encounterDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.encounter.detail.title">Encounter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.encounter.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.encounter.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.forceId}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="hcpNphiesPortalApp.encounter.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.identifier}</dd>
          <dt>
            <span id="encounterClass">
              <Translate contentKey="hcpNphiesPortalApp.encounter.encounterClass">Encounter Class</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.encounterClass}</dd>
          <dt>
            <span id="start">
              <Translate contentKey="hcpNphiesPortalApp.encounter.start">Start</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.start ? <TextFormat value={encounterEntity.start} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="end">
              <Translate contentKey="hcpNphiesPortalApp.encounter.end">End</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.end ? <TextFormat value={encounterEntity.end} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="serviceType">
              <Translate contentKey="hcpNphiesPortalApp.encounter.serviceType">Service Type</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.serviceType}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="hcpNphiesPortalApp.encounter.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{encounterEntity.priority}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.encounter.subject">Subject</Translate>
          </dt>
          <dd>{encounterEntity.subject ? encounterEntity.subject.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.encounter.hospitalization">Hospitalization</Translate>
          </dt>
          <dd>{encounterEntity.hospitalization ? encounterEntity.hospitalization.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.encounter.serviceProvider">Service Provider</Translate>
          </dt>
          <dd>{encounterEntity.serviceProvider ? encounterEntity.serviceProvider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/encounter" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/encounter/${encounterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ encounter }: IRootState) => ({
  encounterEntity: encounter.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EncounterDetail);
