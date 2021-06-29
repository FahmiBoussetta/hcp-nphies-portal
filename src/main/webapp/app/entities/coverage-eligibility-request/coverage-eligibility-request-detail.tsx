import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './coverage-eligibility-request.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageEligibilityRequestDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const coverageEligibilityRequestEntity = useAppSelector(state => state.coverageEligibilityRequest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coverageEligibilityRequestDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.detail.title">CoverageEligibilityRequest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityRequestEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityRequestEntity.guid}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityRequestEntity.parsed}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityRequestEntity.priority}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityRequestEntity.identifier}</dd>
          <dt>
            <span id="servicedDate">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.servicedDate">Serviced Date</Translate>
            </span>
          </dt>
          <dd>
            {coverageEligibilityRequestEntity.servicedDate ? (
              <TextFormat value={coverageEligibilityRequestEntity.servicedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="servicedDateEnd">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.servicedDateEnd">Serviced Date End</Translate>
            </span>
          </dt>
          <dd>
            {coverageEligibilityRequestEntity.servicedDateEnd ? (
              <TextFormat value={coverageEligibilityRequestEntity.servicedDateEnd} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.patient">Patient</Translate>
          </dt>
          <dd>{coverageEligibilityRequestEntity.patient ? coverageEligibilityRequestEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.provider">Provider</Translate>
          </dt>
          <dd>{coverageEligibilityRequestEntity.provider ? coverageEligibilityRequestEntity.provider.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.insurer">Insurer</Translate>
          </dt>
          <dd>{coverageEligibilityRequestEntity.insurer ? coverageEligibilityRequestEntity.insurer.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.facility">Facility</Translate>
          </dt>
          <dd>{coverageEligibilityRequestEntity.facility ? coverageEligibilityRequestEntity.facility.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.coverages">Coverages</Translate>
          </dt>
          <dd>
            {coverageEligibilityRequestEntity.coverages
              ? coverageEligibilityRequestEntity.coverages.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {coverageEligibilityRequestEntity.coverages && i === coverageEligibilityRequestEntity.coverages.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/coverage-eligibility-request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coverage-eligibility-request/${coverageEligibilityRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CoverageEligibilityRequestDetail;
