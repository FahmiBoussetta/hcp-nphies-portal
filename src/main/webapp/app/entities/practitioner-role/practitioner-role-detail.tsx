import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './practitioner-role.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPractitionerRoleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PractitionerRoleDetail = (props: IPractitionerRoleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { practitionerRoleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="practitionerRoleDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.practitionerRole.detail.title">PractitionerRole</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{practitionerRoleEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.practitionerRole.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{practitionerRoleEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.practitionerRole.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{practitionerRoleEntity.forceId}</dd>
          <dt>
            <span id="start">
              <Translate contentKey="hcpNphiesPortalApp.practitionerRole.start">Start</Translate>
            </span>
          </dt>
          <dd>
            {practitionerRoleEntity.start ? <TextFormat value={practitionerRoleEntity.start} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="end">
              <Translate contentKey="hcpNphiesPortalApp.practitionerRole.end">End</Translate>
            </span>
          </dt>
          <dd>
            {practitionerRoleEntity.end ? <TextFormat value={practitionerRoleEntity.end} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.practitionerRole.practitioner">Practitioner</Translate>
          </dt>
          <dd>{practitionerRoleEntity.practitioner ? practitionerRoleEntity.practitioner.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.practitionerRole.organization">Organization</Translate>
          </dt>
          <dd>{practitionerRoleEntity.organization ? practitionerRoleEntity.organization.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/practitioner-role" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/practitioner-role/${practitionerRoleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ practitionerRole }: IRootState) => ({
  practitionerRoleEntity: practitionerRole.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PractitionerRoleDetail);
