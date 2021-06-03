import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payee.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayeeDetail = (props: IPayeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { payeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="payeeDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.payee.detail.title">Payee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{payeeEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.payee.type">Type</Translate>
            </span>
          </dt>
          <dd>{payeeEntity.type}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payee.partyPatient">Party Patient</Translate>
          </dt>
          <dd>{payeeEntity.partyPatient ? payeeEntity.partyPatient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payee.partyOrganization">Party Organization</Translate>
          </dt>
          <dd>{payeeEntity.partyOrganization ? payeeEntity.partyOrganization.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payee/${payeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ payee }: IRootState) => ({
  payeeEntity: payee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayeeDetail);
